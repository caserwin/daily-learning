package dataset.transformation;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * Created by yidxue on 2018/2/8
 */
public class FlinkFlatMapDemo {

    public class Tokenizer implements FlatMapFunction<String, Tuple2<String,Integer>> {
        @Override
        public void flatMap(String value, Collector<Tuple2<String,Integer>> out) {
            for (String token : value.split("\\s+")) {
                out.collect(Tuple2.of(token, 1));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSet<String> inData = env.fromElements("aa bb cc dd", "a bb cc ee");
        DataSet<Tuple2<String,Integer>> wordCounts = inData.flatMap(new FlinkFlatMapDemo().new Tokenizer());
        wordCounts.print();
    }
}
