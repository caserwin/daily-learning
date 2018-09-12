package datastream.window.assigners;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by yidxue on 2018/9/11
 */
public class FlinkStaticSessionWindowsDemo {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);

        // 设置数据源
        DataStream<Tuple3<String, String, Long>> source = env.addSource(new DataSource()).name("Demo Source");

        // 设置水位线
        DataStream<Tuple3<String, String, Long>> stream = source.assignTimestampsAndWatermarks(
            new BoundedOutOfOrdernessTimestampExtractor<Tuple3<String, String, Long>>(Time.seconds(6)) {
                @Override
                public long extractTimestamp(Tuple3<String, String, Long> element) {
                    System.out.println("watermark -> " + getCurrentWatermark().getTimestamp());
                    return element.f2;
                }
            }
        );

        // 窗口聚合
        stream.keyBy(0).window(EventTimeSessionWindows.withGap(Time.seconds(10))).reduce(
            new ReduceFunction<Tuple3<String, String, Long>>() {
                @Override
                public Tuple3<String, String, Long> reduce(Tuple3<String, String, Long> value1, Tuple3<String, String, Long> value2) throws Exception {
                    return Tuple3.of(value1.f0, value1.f1 + "" + value2.f1, 1L);
                }
            }
        ).print();

        env.execute("TimeWindowDemo");
    }

    private static class DataSource extends RichParallelSourceFunction<Tuple3<String, String, Long>> {
        private volatile boolean running = true;

        @Override
        public void run(SourceContext<Tuple3<String, String, Long>> ctx) throws InterruptedException {

            Tuple3[] elements = new Tuple3[]{
                Tuple3.of("a", "1", 1000000050000L),
                Tuple3.of("a", "2", 1000000054000L),
                Tuple3.of("a", "3", 1000000079900L),
                Tuple3.of("a", "4", 1000000115000L),
                Tuple3.of("b", "5", 1000000100000L),
                Tuple3.of("b", "6", 1000000108000L)
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
