package datastream.timetype.eventtime;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by yidxue on 2018/8/23
 */
public class FlinkWatermarkWithPeriodicDemo1 {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置数据源
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        DataStream<Tuple3<String, String, Long>> dataStream = env.addSource(new DataSource()).name("Demo Source");

//        dataStream.print();
        // 设置水位线
        DataStream<Tuple3<String, String, Long>> watermark = dataStream.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks<Tuple3<String, String, Long>>() {
            // //最大允许的乱序时间是10s
            private final long maxOutOfOrderness = 100000L;
            private long currentMaxTimestamp = 0L;

            @Override
            public Watermark getCurrentWatermark() {
//                System.out.println("getCurrentWatermark: " + (currentMaxTimestamp - maxOutOfOrderness));
                return new Watermark(currentMaxTimestamp - maxOutOfOrderness);
            }

            @Override
            public long extractTimestamp(Tuple3<String, String, Long> element, long previousElementTimestamp) {
                long timestamp = element.f2;
                currentMaxTimestamp = Math.max(timestamp, currentMaxTimestamp);
                System.out.println("extractTimestamp: " + currentMaxTimestamp);
                return timestamp;
            }
        });

        watermark.print();

//        // 窗口函数进行处理
//        DataStream<Tuple3<String, String, Long>> resStream = watermark.keyBy(0).timeWindow(Time.seconds(3)).reduce(
//            new ReduceFunction<Tuple3<String, String, Long>>() {
//                @Override
//                public Tuple3<String, String, Long> reduce(Tuple3<String, String, Long> value1, Tuple3<String, String, Long> value2) throws Exception {
//                    return Tuple3.of(value1.f0, value1.f1 + "" + value2.f1, 1L);
//                }
//            }
//        );
//
//        resStream.print();

        env.execute("event time demo");
    }

    private static class DataSource extends RichParallelSourceFunction<Tuple3<String, String, Long>> {
        private volatile boolean running = true;

        @Override
        public void run(SourceContext<Tuple3<String, String, Long>> ctx) throws InterruptedException {
            // 这是我设置watermark 都比 timestamp 早两秒
            Tuple3[] elements = new Tuple3[]{
                Tuple3.of("a", "1", 1000000052000L)
//                Tuple3.of("a", "2", 1000000063000L),
//                Tuple3.of("a", "3", 1000000055000L),
//                Tuple3.of("b", "4", 1000000062000L),
//                Tuple3.of("b", "5", 1000000059000L)
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
