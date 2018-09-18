package datastream.window.predefined;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by yidxue on 2018/9/17
 * 这里要记录下，countWindow 结合 event time + watermark机制，根本不起作用。
 */
public class FlinkCountWindowDemo2 {
    public static void main(String[] args) throws Exception {
        long delay = 5100L;

        final ParameterTool params = ParameterTool.fromArgs(args);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().setGlobalJobParameters(params);
        env.setParallelism(1);
        final int windowSize = params.getInt("window", 3);
        final int slideSize = params.getInt("slide", 2);

        // read source data
        DataStreamSource<Tuple3<String, String, Long>> source = env.addSource(new StreamDataSource());

        // 设置水位线
        DataStream<Tuple3<String, String, Long>> stream = source.assignTimestampsAndWatermarks(
            new BoundedOutOfOrdernessTimestampExtractor<Tuple3<String, String, Long>>(Time.milliseconds(delay)) {
                @Override
                public long extractTimestamp(Tuple3<String, String, Long> element) {
                    System.out.println("watermark -> " + getCurrentWatermark().getTimestamp());
                    return element.f2;
                }
            }
        );

        // calculate
        DataStream<Tuple3<String, String, Long>> outStream = stream
                                                                 .keyBy(0)
                                                                 .countWindow(windowSize)
//                                                           .countWindow(windowSize, slideSize)
                                                                 .reduce(
                                                                     new ReduceFunction<Tuple3<String, String, Long>>() {
                                                                         @Override
                                                                         public Tuple3<String, String, Long> reduce(Tuple3<String, String, Long> value1, Tuple3<String, String, Long> value2) throws Exception {
                                                                             return Tuple3.of(value1.f0, value1.f1 + "" + value2.f1, 1L);
                                                                         }
                                                                     }
                                                                 );
        outStream.print();
        env.execute("WindowWordCount");
    }

    public static class StreamDataSource extends RichParallelSourceFunction<Tuple3<String, String, Long>> {
        private volatile boolean running = true;

        @Override
        public void run(SourceContext<Tuple3<String, String, Long>> ctx) throws InterruptedException {

            Tuple3[] elements = new Tuple3[]{
                Tuple3.of("b", "0", 1000000307800L),
                Tuple3.of("b", "8", 1000000308000L),
                Tuple3.of("b", "7", 1000000208000L),
                Tuple3.of("a", "1", 1000000054000L),
                Tuple3.of("a", "2", 1000000079900L),
                Tuple3.of("a", "5", 1000000100000L),
                Tuple3.of("a", "4", 1000000115000L),
                Tuple3.of("a", "3", 1000000050000L),
                Tuple3.of("a", "6", 1000000108000L),
                Tuple3.of("b", "9", 1000000307500L)
            };

            int count = 0;
            while (running && count < elements.length) {
                ctx.collect(new Tuple3<>((String) elements[count].f0, (String) elements[count].f1, (Long) elements[count].f2));
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
