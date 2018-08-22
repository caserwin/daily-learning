package datastream.timetype.processtime;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by yidxue on 2018/8/22
 */
public class FlinkProcessTimeDemo3 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);

        // 设置数据源
        DataStream<Tuple3<Integer, String, Long>> source = env.fromElements(
//            Tuple3.of(1, "c", 1534582000000L),
            Tuple3.of(1, "a", 1534581003500L),
            Tuple3.of(4, "a", 1534581004400L),
            Tuple3.of(8, "a", 1534581006000L),
            Tuple3.of(2, "b", 1534581002600L),
            Tuple3.of(4, "b", 1534581004400L)
        );

        source.keyBy(1).timeWindow(Time.milliseconds(10)).sum(0).print();

        env.execute("TimeWindowDemo");
    }
}
