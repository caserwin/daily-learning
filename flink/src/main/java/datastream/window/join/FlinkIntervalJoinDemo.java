package datastream.window.join;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.ProcessJoinFunction;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import util.source.StreamDataSource;
import util.source.StreamDataSource1;

/**
 * Created by yidxue on 2018/9/18
 * Interval Join 中的元素必须是 watermark 之上。
 */
public class FlinkIntervalJoinDemo {
    public static void main(String[] args) throws Exception {
        long delay = 7000L;
        long positive = 5000L;
        long negative = -5000L;

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);

        // 设置数据源
        DataStream<Tuple3<String, String, Long>> leftSource = env.addSource(new StreamDataSource()).name("Demo Source");
        DataStream<Tuple3<String, String, Long>> rightSource = env.addSource(new StreamDataSource1()).name("Demo Source");

        // 设置水位线
        DataStream<Tuple3<String, String, Long>> leftStream = leftSource.assignTimestampsAndWatermarks(
            new BoundedOutOfOrdernessTimestampExtractor<Tuple3<String, String, Long>>(Time.milliseconds(delay)) {
                @Override
                public long extractTimestamp(Tuple3<String, String, Long> element) {
                    return element.f2;
                }
            }
        );

        DataStream<Tuple3<String, String, Long>> rigjhtStream = rightSource.assignTimestampsAndWatermarks(
            new BoundedOutOfOrdernessTimestampExtractor<Tuple3<String, String, Long>>(Time.milliseconds(delay)) {
                @Override
                public long extractTimestamp(Tuple3<String, String, Long> element) {
                    return element.f2;
                }
            }
        );

        // interval Join
        leftStream
            .keyBy(new LeftSelectKey())
            .intervalJoin(rigjhtStream.keyBy(new RightSelectKey()))
            .between(Time.milliseconds(negative), Time.milliseconds(positive))
            .process(new ProcessJoinFunction<Tuple3<String, String, Long>, Tuple3<String, String, Long>, Tuple5<String, String, String, Long, Long>>() {
                @Override
                public void processElement(Tuple3<String, String, Long> left, Tuple3<String, String, Long> right, Context ctx, Collector<Tuple5<String, String, String, Long, Long>> out) {
                    out.collect(Tuple5.of(left.f0, left.f1, right.f1, left.f2, right.f2));
                }
            }).print();


        env.execute("TimeWindowDemo");
    }

    public static class LeftSelectKey implements KeySelector<Tuple3<String, String, Long>, String> {
        @Override
        public String getKey(Tuple3<String, String, Long> w) {
            return w.f0;
        }
    }

    public static class RightSelectKey implements KeySelector<Tuple3<String, String, Long>, String> {
        @Override
        public String getKey(Tuple3<String, String, Long> w) {
            return w.f0;
        }
    }
}