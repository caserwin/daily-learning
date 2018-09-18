package datastream.window.predefined;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

/**
 * @author yidxue
 */
public class FlinkCountWindowDemo1 {

    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().setGlobalJobParameters(params);
        env.setParallelism(1);
        final int windowSize = params.getInt("window", 3);
        final int slideSize = params.getInt("slide", 2);

        // read source data
        DataStreamSource<Tuple2<String, String>> inStream = env.addSource(new StreamDataSource());

        // calculate
        DataStream<Tuple2<String, String>> outStream = inStream
                                                           .keyBy(0)
//                                                           .countWindow(windowSize)
                                                           .countWindow(windowSize, slideSize)
                                                           .reduce(
                                                               new ReduceFunction<Tuple2<String, String>>() {
                                                                   @Override
                                                                   public Tuple2<String, String> reduce(Tuple2<String, String> value1, Tuple2<String, String> value2) throws Exception {
                                                                       return Tuple2.of(value1.f0, value1.f1 + "" + value2.f1);
                                                                   }
                                                               }
                                                           );
        outStream.print();
        env.execute("WindowWordCount");
    }

    public static class StreamDataSource extends RichParallelSourceFunction<Tuple2<String, String>> {
        private volatile boolean running = true;

        @Override
        public void run(SourceContext<Tuple2<String, String>> ctx) throws InterruptedException {

            Tuple2[] elements = new Tuple2[]{
                Tuple2.of("a", "1"),
                Tuple2.of("a", "2"),
                Tuple2.of("a", "3"),
                Tuple2.of("a", "4"),
                Tuple2.of("a", "5"),
                Tuple2.of("a", "6"),
                Tuple2.of("b", "7"),
                Tuple2.of("b", "8"),
                Tuple2.of("b", "9"),
                Tuple2.of("b", "0")
            };

            int count = 0;
            while (running && count < elements.length) {
                ctx.collect(new Tuple2<>((String) elements[count].f0, (String) elements[count].f1));
                count++;
                Thread.sleep(1000);
            }
        }

        @Override
        public void cancel() {
            running = false;
        }
    }
}
