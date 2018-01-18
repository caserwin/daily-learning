package core.hdfs;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.hadoop.mapred.HadoopInputFormat;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;

/**
 * @author yidxue
 * reference: https://flink.apache.org/news/2014/11/18/hadoop-compatibility.html
 */
public class FlinkReadHDFSLzo {

    private static final String TIMING_PROD_PATH = "hdfs://rpsj1hmn001.webex.com:8020/kafka-bak/timing_telemetry_hdfs/timing_telemetry_hdfs.2017-12-31.lzo";

    private static JobConf getConfiguration() {
        JobConf conf = new JobConf();
        conf.set("io.compression.codecs", "com.hadoop.compression.lzo.LzopCodec");
        TextInputFormat.addInputPath(conf, new Path(TIMING_PROD_PATH));
        return conf;
    }

    public static void main(String[] args){
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(1000, 1000));
        env.setParallelism(10);

        // Set up the Hadoop TextInputFormat for JMTCall.
        JobConf jmtCallConf = getConfiguration();
        HadoopInputFormat<LongWritable, Text> inputSet = new HadoopInputFormat<>(new TextInputFormat(), LongWritable.class, Text.class, jmtCallConf);
        DataSet<Tuple2<LongWritable, Text>> rawCall = env.createInput(inputSet);

        try {
            System.out.println(rawCall.count());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
