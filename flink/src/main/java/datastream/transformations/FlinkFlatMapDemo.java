package datastream.transformations;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * Created by yidxue on 2018/5/14
 * DataStream → DataStream
 * @author yidxue
 */
public class FlinkFlatMapDemo {

    public static class Tokenizer implements FlatMapFunction<String, String> {
        @Override
        public void flatMap(String value, Collector<String> out) {
            for (String token : value.split("\\s+")) {
                out.collect(token);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> dStream = env.fromElements("aaa bbb", "aa bb", "aaa aa");
        DataStream<String> reStream = dStream.flatMap(new Tokenizer());
        reStream.print();

        // start run
        env.execute("Demo");
    }
}
