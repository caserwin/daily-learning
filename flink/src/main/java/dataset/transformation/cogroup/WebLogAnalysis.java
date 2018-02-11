package dataset.transformation.cogroup;

import dataset.transformation.cogroup.util.WebLogData;
import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.FunctionAnnotation.ForwardedFieldsFirst;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.util.Collector;

import java.util.List;

/**
 * Code from: https://github.com/apache/flink/tree/master/flink-examples/flink-examples-batch/src/main/java/org/apache/flink/examples/java/relational
 */
public class WebLogAnalysis {

    // *************************************************************************
    //     PROGRAM
    // *************************************************************************

    public static void main(String[] args) throws Exception {

        final ParameterTool params = ParameterTool.fromArgs(args);

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        // get input data
        DataSet<Tuple2<String, String>> documents = getDocumentsDataSet(env, params);
        DataSet<Tuple3<Integer, String, Integer>> ranks = getRanksDataSet(env, params);
        DataSet<Tuple2<String, String>> visits = getVisitsDataSet(env, params);

        // Retain documents with keywords
        DataSet<Tuple1<String>> filterDocs = documents
                .filter(new FilterDocByKeyWords())
                .project(0);

        // Filter ranks by minimum rank
        DataSet<Tuple3<Integer, String, Integer>> filterRanks = ranks
                .filter(new FilterByRank());

        // Filter visits by visit date
        DataSet<Tuple1<String>> filterVisits = visits
                .filter(new FilterVisitsByDate())
                .project(0);

//
//        List<Tuple1<String>> docLS = filterDocs.collect();
//        for (Tuple1<String> tuple : docLS) {
//            System.out.println("===doc===\t" + tuple.f0);
//        }
//
//        List<Tuple3<Integer, String, Integer>> rankLS = filterRanks.collect();
//        for (Tuple3<Integer, String, Integer> tuple : rankLS) {
//            System.out.println("===rank===\t" + tuple.f0 + "-->" + tuple.f1 + "-->" + tuple.f2);
//        }
//

        // Join the filtered documents and ranks, i.e., get all URLs with min rank and keywords
        DataSet<Tuple3<Integer, String, Integer>> joinDocsRanks =
                filterDocs.join(filterRanks)
                        .where(0).equalTo(1)
                        .projectSecond(0, 1, 2);

        // Anti-join urls with visits, i.e., retain all URLs which have NOT been visited in a certain time
        DataSet<Tuple3<Integer, String, Integer>> result =
                joinDocsRanks.coGroup(filterVisits)
                        .where(1).equalTo(0)
                        .with(new AntiJoinVisits());


        List<Tuple3<Integer, String, Integer>> resLS = result.collect();
        for (Tuple3<Integer, String, Integer> tuple : resLS) {
            System.out.println("==========\t" + tuple.f0 + "-->" + tuple.f1 + "-->" + tuple.f2);
        }


//        // emit result
//        if (params.has("output")) {
//            result.writeAsCsv(params.get("output"), "\n", "|");
//            // execute program
//            env.execute("WebLogAnalysis Example");
//        } else {
//            System.out.println("Printing result to stdout. Use --output to specify output path.");
//            result.print();
//        }
    }

    // *************************************************************************
    //     USER FUNCTIONS
    // *************************************************************************

    /**
     * MapFunction that filters for documents that contain a certain set of
     * keywords.
     */
    public static class FilterDocByKeyWords implements FilterFunction<Tuple2<String, String>> {

        private static final String[] KEYWORDS = {" editors ", " oscillations "};

        /**
         * Filters for documents that contain all of the given keywords and projects the records on the URL field.
         * <p>
         * <p>Output Format:
         * 0: URL
         * 1: DOCUMENT_TEXT
         */
        @Override
        public boolean filter(Tuple2<String, String> value) throws Exception {
            // FILTER
            // Only collect the document if all keywords are contained
            String docText = value.f1;
            for (String kw : KEYWORDS) {
                if (!docText.contains(kw)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * MapFunction that filters for records where the rank exceeds a certain threshold.
     */
    public static class FilterByRank implements FilterFunction<Tuple3<Integer, String, Integer>> {

        private static final int RANKFILTER = 40;

        /**
         * Filters for records of the rank relation where the rank is greater
         * than the given threshold.
         * <p>
         * <p>Output Format:
         * 0: RANK
         * 1: URL
         * 2: AVG_DURATION
         */
        @Override
        public boolean filter(Tuple3<Integer, String, Integer> value) throws Exception {
            return (value.f0 > RANKFILTER);
        }
    }

    /**
     * MapFunction that filters for records of the visits relation where the year
     * (from the date string) is equal to a certain value.
     */
    public static class FilterVisitsByDate implements FilterFunction<Tuple2<String, String>> {

        private static final int YEARFILTER = 2007;

        /**
         * Filters for records of the visits relation where the year of visit is equal to a
         * specified value. The URL of all visit records passing the filter is emitted.
         * <p>
         * <p>Output Format:
         * 0: URL
         * 1: DATE
         */
        @Override
        public boolean filter(Tuple2<String, String> value) throws Exception {
            // Parse date string with the format YYYY-MM-DD and extract the year
            String dateString = value.f1;
            int year = Integer.parseInt(dateString.substring(0, 4));
            return (year == YEARFILTER);
        }
    }


    /**
     * CoGroupFunction that realizes an anti-join.
     * If the first input does not provide any pairs, all pairs of the second input are emitted.
     * Otherwise, no pair is emitted.
     */
    @ForwardedFieldsFirst("*")
    public static class AntiJoinVisits implements CoGroupFunction<Tuple3<Integer, String, Integer>, Tuple1<String>, Tuple3<Integer, String, Integer>> {

        /**
         * If the visit iterator is empty, all pairs of the rank iterator are emitted.
         * Otherwise, no pair is emitted.
         * <p>
         * <p>Output Format:
         * 0: RANK
         * 1: URL
         * 2: AVG_DURATION
         */
        @Override
        public void coGroup(Iterable<Tuple3<Integer, String, Integer>> ranks, Iterable<Tuple1<String>> visits, Collector<Tuple3<Integer, String, Integer>> out) {
            // Check if there is a entry in the visits relation
            for (Tuple3<Integer, String, Integer> next : ranks) {
                for (Tuple1<String> v : visits) {
                    out.collect(next);
                }
            }
        }
    }

    // *************************************************************************
    //     UTIL METHODS
    // *************************************************************************

    private static DataSet<Tuple2<String, String>> getDocumentsDataSet(ExecutionEnvironment env, ParameterTool params) {
        // Create DataSet for documents relation (URL, Doc-Text)
        if (params.has("documents")) {
            return env.readCsvFile(params.get("documents"))
                    .fieldDelimiter("|")
                    .types(String.class, String.class);
        } else {
            System.out.println("Executing WebLogAnalysis example with default documents data set.");
            System.out.println("Use --documents to specify file input.");
            return WebLogData.getDocumentDataSet(env);
        }
    }

    private static DataSet<Tuple3<Integer, String, Integer>> getRanksDataSet(ExecutionEnvironment env, ParameterTool params) {
        // Create DataSet for ranks relation (Rank, URL, Avg-Visit-Duration)
        if (params.has("ranks")) {
            return env.readCsvFile(params.get("ranks"))
                    .fieldDelimiter("|")
                    .types(Integer.class, String.class, Integer.class);
        } else {
            System.out.println("Executing WebLogAnalysis example with default ranks data set.");
            System.out.println("Use --ranks to specify file input.");
            return WebLogData.getRankDataSet(env);
        }
    }

    private static DataSet<Tuple2<String, String>> getVisitsDataSet(ExecutionEnvironment env, ParameterTool params) {
        // Create DataSet for visits relation (URL, Date)
        if (params.has("visits")) {
            return env.readCsvFile(params.get("visits"))
                    .fieldDelimiter("|")
                    .includeFields("011000000")
                    .types(String.class, String.class);
        } else {
            System.out.println("Executing WebLogAnalysis example with default visits data set.");
            System.out.println("Use --visits to specify file input.");
            return WebLogData.getVisitDataSet(env);
        }
    }

}