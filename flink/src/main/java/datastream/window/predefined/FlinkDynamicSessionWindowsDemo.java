package datastream.window.predefined;

import util.source.DataSource;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.assigners.SessionWindowTimeGapExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by yidxue on 2018/9/11
 */
public class FlinkDynamicSessionWindowsDemo {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);

        // 设置数据源
        DataStream<Tuple3<String, String, Long>> source = env.addSource(new DataSource()).name("Demo Source");

        // 设置水位线
        DataStream<Tuple3<String, String, Long>> stream = source.assignTimestampsAndWatermarks(
            new BoundedOutOfOrdernessTimestampExtractor<Tuple3<String, String, Long>>(Time.seconds(6)) {
                @Override
                public long extractTimestamp(Tuple3<String, String, Long> element) {
                    System.out.println("watermark -> " + getCurrentWatermark().getTimestamp());
                    return element.f2;
                }
            }
        );

        // 窗口聚合
        stream.keyBy(0)
            .window(EventTimeSessionWindows.withDynamicGap(new SessionWindowTimeGapExtractor<Tuple3<String, String, Long>>() {
                @Override
                public long extract(Tuple3<String, String, Long> element) {
                    return 10000L;
                }
            }))
            .reduce(new ReduceFunction<Tuple3<String, String, Long>>() {
                        @Override
                        public Tuple3<String, String, Long> reduce(Tuple3<String, String, Long> value1, Tuple3<String, String, Long> value2) throws Exception {
                            return Tuple3.of(value1.f0, value1.f1 + "" + value2.f1, 1L);
                        }
                    }
            )
            .print();

        env.execute("TimeWindowDemo");
    }
}
