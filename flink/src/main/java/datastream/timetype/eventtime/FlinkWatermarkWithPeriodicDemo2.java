package datastream.timetype.eventtime;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;
import java.text.SimpleDateFormat;

/**
 * Created by yidxue on 2018/8/22
 * @author yidxue
 * https://blog.csdn.net/lmalds/article/details/52704170
 */
public class FlinkWatermarkWithPeriodicDemo2 {

    public static class StrToTupleMap implements MapFunction<String, Tuple2<String, Long>> {
        @Override
        public Tuple2<String, Long> map(String in) {
            String[] tmp = in.trim().split("\\W+");
            return Tuple2.of(tmp[0], Long.parseLong(tmp[1]));
        }
    }

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // 数据源
        DataStream<Tuple2<String, Long>> dataStream = env.socketTextStream("localhost", 9999).map(new StrToTupleMap());

        // 设置水位线
        DataStream<Tuple2<String, Long>> watermark = dataStream.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks<Tuple2<String, Long>>() {
            // //最大允许的乱序时间是10s
            private final long maxOutOfOrderness = 10000L;
            private long currentMaxTimestamp = 0L;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Watermark a = null;

            @Override
            public Watermark getCurrentWatermark() {
                a = new Watermark(currentMaxTimestamp - maxOutOfOrderness);
                return a;
            }

            @Override
            public long extractTimestamp(Tuple2<String, Long> element, long previousElementTimestamp) {
                long timestamp = element.f1;
                currentMaxTimestamp = Math.max(timestamp, currentMaxTimestamp);
                System.out.println("timestamp:" + element.f0 + "," + element.f1 + "|" + format.format(element.f1) + "," + currentMaxTimestamp + "|" + format.format(currentMaxTimestamp) + a.toString());
                return timestamp;
            }
        });

        watermark.print();
        //
//        val window = watermark
//                         .keyBy(_._1)
//                         .window(TumblingEventTimeWindows.of(Time.seconds(3)))
//                         .apply(new WindowFunctionTest)
//
//        window.print()

        env.execute("event time demo");
    }
}
