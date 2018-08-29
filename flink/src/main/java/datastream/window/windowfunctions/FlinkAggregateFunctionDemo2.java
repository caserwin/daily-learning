package datastream.window.windowfunctions;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by yidxue on 2018/8/21
 * @author yidxue
 */
public class FlinkAggregateFunctionDemo2 {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // 设置数据源
        DataStream<Tuple3<String, Long, Integer>> source = env.fromElements(
            Tuple3.of("a", 4L, 1534581000),
            Tuple3.of("b", 3L, 1534581005),
            Tuple3.of("a", 2L, 1534581010),
            Tuple3.of("b", 7L, 1534581015),
            Tuple3.of("a", 8L, 1534581020)
        );

        // 设置水位线
        DataStream<Tuple3<String, Long, Integer>> stream = source.assignTimestampsAndWatermarks(
            new BoundedOutOfOrdernessTimestampExtractor<Tuple3<String, Long, Integer>>(Time.seconds(10)) {
                @Override
                public long extractTimestamp(Tuple3<String, Long, Integer> element) {
                    return element.f2;
                }
            }
        );

        // 窗口聚合
        stream
            .keyBy(0)
            .window(TumblingEventTimeWindows.of(Time.seconds(1)))
            .aggregate(new MyAggregateFunction())
            .print();

        env.execute("FlinkWindowAggregateFunctionDemo");
    }

    public static class MyAggregateFunction implements AggregateFunction<Tuple3<String, Long, Integer>, Tuple3<String, Long, Long>, Tuple2<String, Double>> {
        @Override
        public Tuple3<String, Long, Long> createAccumulator() {
            return new Tuple3<>("", 0L, 0L);
        }

        @Override
        public Tuple3<String, Long, Long> add(Tuple3<String, Long, Integer> value, Tuple3<String, Long, Long> accumulator) {
            return new Tuple3<>(value.f0, accumulator.f1 + value.f1, accumulator.f2 + 1L);
        }

        @Override
        public Tuple2<String, Double> getResult(Tuple3<String, Long, Long> accumulator) {
            return Tuple2.of(accumulator.f0, ((double) accumulator.f1) / accumulator.f2);
        }

        @Override
        public Tuple3<String, Long, Long> merge(Tuple3<String, Long, Long> a, Tuple3<String, Long, Long> b) {
            return new Tuple3<>(a.f0, a.f1 + b.f1, a.f2 + b.f2);
        }
    }
}
