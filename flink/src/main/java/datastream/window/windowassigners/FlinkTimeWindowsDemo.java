package datastream.window.windowassigners;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by yidxue on 2018/8/18
 *
 * 非常有疑问！！！
 */
public class FlinkTimeWindowsDemo {

    public static void main(String[] args) throws Exception {
//        System.out.println(System.currentTimeMillis()/1000);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // 设置数据源
        DataStream<Tuple3<Integer, String, Long>> source = env.fromElements(
//            Tuple3.of(1, "c", 1534582000000L),
            Tuple3.of(1, "a", 1534581003500L),
            Tuple3.of(4, "a", 1534581004400L),
            Tuple3.of(8, "a", 1534581006000L),
            Tuple3.of(2, "b", 1534581002600L),
            Tuple3.of(4, "b", 1534581004400L)
        );

        // 设置水位线
        DataStream<Tuple3<Integer, String, Long>> stream = source.assignTimestampsAndWatermarks(
            new BoundedOutOfOrdernessTimestampExtractor<Tuple3<Integer, String, Long>>(Time.seconds(1)) {
                @Override
                public long extractTimestamp(Tuple3<Integer, String, Long> element) {
                    return element.f2;
                }
            }
        );

        // 窗口聚合
        stream.keyBy(1).timeWindow(Time.seconds(1)).sum(0).print();

        env.execute("TimeWindowDemo");
    }
}
