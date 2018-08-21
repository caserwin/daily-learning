package datastream.window.windowassigners;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author yidxue
 */
public class FlinkCountWindowDemo {

    public static final class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
            // normalize and split the line
            String[] tokens = value.toLowerCase().split("\\W+");

            // emit the pairs
            for (String token : tokens) {
                if (token.length() > 0) {
                    out.collect(new Tuple2<>(token, 1));
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        String[] words = new String[]{"a a b b a b a b c c a a"};
        DataStream<String> text = env.fromElements(words);

        // make parameters available in the web interface
        env.getConfig().setGlobalJobParameters(params);

        final int windowSize = params.getInt("window", 3);
        final int slideSize = params.getInt("slide", 2);

        DataStream<Tuple2<String, Integer>> tmp = text.flatMap(new Tokenizer());

        tmp.project(0).print();
        // create windows of windowSize records slided every slideSize records
        DataStream<Tuple2<String, Integer>> counts = tmp
                                                         .keyBy(0)
//                                                         .countWindow(windowSize)
                                                         .countWindow(windowSize, slideSize)
                                                         .sum(1);

        counts.print();
        env.execute("WindowWordCount");
    }
}
