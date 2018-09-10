package datastream.timetype.eventtime;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by yidxue on 2018/8/23
 * 在数据源就设置好时间戳
 * https://ci.apache.org/projects/flink/flink-docs-release-1.6/dev/event_timestamps_watermarks.html#source-functions-with-timestamps-and-watermarks
 */
public class FlinkWaterMarkWithSourceDemo {

    public static void main(String[] args) throws Exception {
        int windowSize = 10;

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // 数据源
        DataStream<Tuple3<String, String, Long>> dataStream = env.addSource(new DataSource()).name("Demo Source");

        // 窗口函数进行处理
        DataStream<Tuple3<String, String, Long>> resStream = dataStream.keyBy(0).timeWindow(Time.seconds(windowSize)).reduce(
            new ReduceFunction<Tuple3<String, String, Long>>() {
                @Override
                public Tuple3<String, String, Long> reduce(Tuple3<String, String, Long> value1, Tuple3<String, String, Long> value2) throws Exception {
                    return Tuple3.of(value1.f0, value1.f1 + "" + value2.f1, 1L);
                }
            }
        );

        resStream.print();
        env.execute("event time demo");
    }

    private static class DataSource extends RichParallelSourceFunction<Tuple3<String, String, Long>> {
        private volatile boolean running = true;
        long delay = 5100L;

        @Override
        public void run(SourceContext<Tuple3<String, String, Long>> ctx) {
            // 这是我设置watermark 都比 timestamp 早两秒
            Tuple4[] elements = new Tuple4[]{
                Tuple4.of("a", "1", 1000000050000L, 1000000050000L - delay),
                Tuple4.of("a", "2", 1000000054000L, 1000000054000L - delay),
                Tuple4.of("a", "3", 1000000079900L, 1000000079900L - delay),
                Tuple4.of("a", "4", 1000000115000L, 1000000115000L - delay),
                Tuple4.of("b", "5", 1000000100000L, 1000000100000L - delay),
                Tuple4.of("b", "6", 1000000108000L, 1000000108000L - delay)
            };

            int count = 0;
            while (running && count < elements.length) {
                ctx.collectWithTimestamp(new Tuple3<>((String) elements[count].f0, (String) elements[count].f1, (long) elements[count].f2), (long) elements[count].f2);
                ctx.emitWatermark(new Watermark((long) elements[count].f3));
                count++;
            }
        }

        @Override
        public void cancel() {
            running = false;
        }
    }
}
