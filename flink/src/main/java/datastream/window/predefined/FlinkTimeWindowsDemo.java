package datastream.window.predefined;

import util.source.StreamDataSource;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by yidxue on 2018/8/18
 * nc -lk 9999
 */
public class FlinkTimeWindowsDemo {

    public static void main(String[] args) throws Exception {
        int windowSize = 10;
        long delay = 5100L;

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);

        // 设置数据源
        DataStream<Tuple3<String, String, Long>> source = env.addSource(new StreamDataSource()).name("Demo Source");

//        DataStream<Tuple3<String, String, Long>> source = env.fromElements(
//            Tuple3.of("a", "1", 1000000050000L),
//            Tuple3.of("a", "2", 1000000054000L),
//            Tuple3.of("a", "3", 1000000079900L),
//            Tuple3.of("a", "4", 1000000115000L),
//            Tuple3.of("b", "5", 1000000100000L),
//            Tuple3.of("b", "6", 1000000108000L)
//        );

//        DataStream<Tuple3<String, String, Long>> source = env.socketTextStream("localhost", 9994).name("Demo Source").map(new SocketSourceMap());

        // 设置水位线
        DataStream<Tuple3<String, String, Long>> stream = source.assignTimestampsAndWatermarks(
            new BoundedOutOfOrdernessTimestampExtractor<Tuple3<String, String, Long>>(Time.milliseconds(delay)) {
                @Override
                public long extractTimestamp(Tuple3<String, String, Long> element) {
                    System.out.println("watermark -> " + getCurrentWatermark().getTimestamp());
                    return element.f2;
                }
            }
        );

        // 窗口聚合
        stream.keyBy(0).timeWindow(Time.seconds(windowSize)).reduce(
            new ReduceFunction<Tuple3<String, String, Long>>() {
                @Override
                public Tuple3<String, String, Long> reduce(Tuple3<String, String, Long> value1, Tuple3<String, String, Long> value2) throws Exception {
                    return Tuple3.of(value1.f0, value1.f1 + "" + value2.f1, 1L);
                }
            }
        ).print();

        env.execute("TimeWindowDemo");
    }

    public static class SocketSourceMap implements MapFunction<String, Tuple3<String, String, Long>> {
        @Override
        public Tuple3<String, String, Long> map(String in) {
            String[] strs = in.split(",");
            return Tuple3.of(strs[0], strs[1], Long.parseLong(strs[2]));
        }
    }
}
