package dataset;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

/**
 * @author yidxue
 */
public class FlinkReadHDFS {
    private static final String SAP_PATH = "hdfs://10.29.42.40:8020/user/pda/jmtForCall/jmtdata/jmtdata_AllSites_2018-01-01.csv";

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSet<Tuple2<LongWritable, Text>> tuples = env.readHadoopFile(new TextInputFormat(), LongWritable.class, Text.class, SAP_PATH);

        System.out.println(tuples.collect().size());
    }
}
