package datastream.timetype.eventtime;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple6;
import org.apache.flink.shaded.guava18.com.google.common.collect.Lists;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by yidxue on 2018/8/22
 * https://blog.csdn.net/lmalds/article/details/52704170
 *
 * <p>
 * nc -lk 9990
 * kill -9 $(lsof -t -i:9999)
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

        //
        watermark.keyBy(0).window(TumblingEventTimeWindows.of(Time.seconds(3))).apply(new WindowFunctionTest());

        env.execute("event time demo");
    }

    public static class WindowFunctionTest implements WindowFunction<Tuple2<String, Long>, Tuple6<String, Integer, String, String, String, String>, Tuple, TimeWindow> {
        @Override
        public void apply(Tuple key, TimeWindow window, Iterable<Tuple2<String, Long>> input, Collector<Tuple6<String, Integer, String, String, String, String>> out) {
            ArrayList<Tuple2<String, Long>> list = Lists.newArrayList(input);
            long maxtimestamp = Long.MIN_VALUE;
            long mintimestamp = Long.MAX_VALUE;
            for (Tuple2<String, Long> tuple2 : list) {
                maxtimestamp = Math.max(maxtimestamp, tuple2.f1);
                mintimestamp = Math.min(mintimestamp, tuple2.f1);
            }

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            out.collect(Tuple6.of(key.toString(), list.size(), format.format(maxtimestamp), format.format(mintimestamp), format.format(window.getStart()), format.format(window.getEnd())));
        }
    }
}
