package datastream.timetype.eventtime;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
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
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // 数据源
        DataStream<Tuple2<String, String>> dataStream = env.addSource(new DataSource()).name("Demo Source");

        // 窗口函数进行处理
        DataStream<Tuple2<String, String>> resStream = dataStream.keyBy(0).timeWindow(Time.seconds(3)).reduce(
            new ReduceFunction<Tuple2<String, String>>() {
                @Override
                public Tuple2<String, String> reduce(Tuple2<String, String> value1, Tuple2<String, String> value2) throws Exception {
                    return Tuple2.of(value1.f0, value1.f1 + "" + value2.f1);
                }
            }
        );

        resStream.print();
        env.execute("event time demo");
    }

    private static class DataSource extends RichParallelSourceFunction<Tuple2<String, String>> {
        private volatile boolean running = true;

        @Override
        public void run(SourceContext<Tuple2<String, String>> ctx) {
            // 这是我设置watermark 都比 timestamp 早两秒
            Tuple4[] elements = new Tuple4[]{
                Tuple4.of("a", "1", 1461756862000L, 1461756860000L),
                Tuple4.of("a", "2", 1461756863000L, 1461756862000L),
                Tuple4.of("a", "3", 1461756861000L, 1461756858000L),
                Tuple4.of("b", "4", 1461756862000L, 1461756860000L),
                Tuple4.of("b", "5", 1461756859000L, 1461756862000L)
            };

            int count = 0;
            while (running && count < elements.length) {
                ctx.collectWithTimestamp(new Tuple2<>((String) elements[count].f0, (String) elements[count].f1), (long) elements[count].f2);
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
