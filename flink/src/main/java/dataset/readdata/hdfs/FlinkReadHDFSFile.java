package dataset.readdata.hdfs;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;

/**
 * @author yidxue
 */
public class FlinkReadHDFSFile {
    private static final String SAP_PATH = "hdfs://10.29.42.40:8020/user/pda/jmtForCall/jmtdata/jmtdata_AllSites_2018-01-01.csv";

    public static void main(String[] args) throws Exception {

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSet<String> csvInput = env.readTextFile(SAP_PATH);
        csvInput.first(10).print();

        /*
        以下示例的readHadoopFile()方法在flink 1.3.2以及之前的版本可用，可直接读取csv, textfile文件.
        其中 DataSet<Tuple2<LongWritable, Text>> LongWritable 类型参数是指行数，Text 类型参数是文本内容。

        DataSet<Tuple2<LongWritable, Text>> tuples = env.readHadoopFile(new TextInputFormat(), LongWritable.class, Text.class, SAP_PATH);
        System.out.println(tuples.collect().size());
        */
    }
}
