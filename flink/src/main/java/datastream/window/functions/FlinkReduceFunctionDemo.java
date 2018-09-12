package datastream.window.functions;

import datastream.datasource.DataSource;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by yidxue on 2018/8/21
 */
public class FlinkReduceFunctionDemo {

    public static void main(String[] args) throws Exception {
        int windowSize = 10;
        long delay = 5100L;

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);

        // 设置数据源
        DataStream<Tuple3<String, String, Long>> source = env.addSource(new DataSource()).name("Demo Source");

        // 设置水位线
        DataStream<Tuple3<String, String, Long>> stream = source.assignTimestampsAndWatermarks(
            new BoundedOutOfOrdernessTimestampExtractor<Tuple3<String, String, Long>>(Time.milliseconds(delay)) {
                @Override
                public long extractTimestamp(Tuple3<String, String, Long> element) {
                    return element.f2;
                }
            }
        );

        // 窗口聚合
        stream
            .keyBy(0)
            .window(TumblingEventTimeWindows.of(Time.seconds(windowSize)))
            .reduce(new ReduceFunction<Tuple3<String, String, Long>>() {
                @Override
                public Tuple3<String, String, Long> reduce(Tuple3<String, String, Long> e1, Tuple3<String, String, Long> e2) {
                    return new Tuple3<>(e1.f0, e1.f1 + e2.f1, 1L);
                }
            })
            .print();

        env.execute("FlinkWindowReduceFunctionDemo");
    }
}