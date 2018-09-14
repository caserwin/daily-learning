package datastream.timetype.eventtime;

import util.source.StreamDataSource;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.time.Time;
import javax.annotation.Nullable;

/**
 * Created by yidxue on 2018/9/10
 */
public class FlinkWaterMarkWithPunctuatedDemo {
    public static void main(String[] args) throws Exception {
        long delay = 5100L;
        int windowSize = 10;

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置数据源
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        DataStream<Tuple3<String, String, Long>> dataStream = env.addSource(new StreamDataSource()).name("Demo Source");

        // 设置水位线
        DataStream<Tuple3<String, String, Long>> watermark = dataStream.assignTimestampsAndWatermarks(new AssignerWithPunctuatedWatermarks<Tuple3<String, String, Long>>() {
            //最大允许的乱序时间是10s
            private final long maxOutOfOrderness = delay;
            private long currentMaxTimestamp = 0L;
            Watermark newWM = null;

            @Override
            public long extractTimestamp(Tuple3<String, String, Long> element, long previousElementTimestamp) {
                long timestamp = element.f2;
                currentMaxTimestamp = Math.max(timestamp, currentMaxTimestamp);
                return timestamp;
            }

            @Nullable
            @Override
            public Watermark checkAndGetNextWatermark(Tuple3<String, String, Long> lastElement, long extractedTimestamp) {
                if (newWM == null) {
                    System.out.println("watermark: " + "null");
                } else {
                    System.out.println("watermark: " + newWM.getTimestamp());
                }

                if ("b".equals(lastElement.f0)) {
                    newWM = new Watermark(currentMaxTimestamp - maxOutOfOrderness);
                    return newWM;
                } else {
                    return null;
                }
            }
        });

        // 窗口函数进行处理
        DataStream<Tuple3<String, String, Long>> resStream = watermark.keyBy(0).timeWindow(Time.seconds(windowSize)).reduce(
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
}
