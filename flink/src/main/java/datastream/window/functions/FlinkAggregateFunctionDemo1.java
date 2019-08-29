package datastream.window.functions;

import util.source.StreamDataSource;
import org.apache.flink.api.common.functions.AggregateFunction;
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
public class FlinkAggregateFunctionDemo1 {

    public static void main(String[] args) throws Exception {
        int windowSize = 10;
        long delay = 5000L;

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);

        // 设置数据源
        DataStream<Tuple3<String, String, Long>> source = env.addSource(new StreamDataSource()).name("Demo Source");

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
            .aggregate(new MyAggregateFunction())
            .print();

        env.execute("FlinkWindowAggregateFunctionDemo");
    }

    public static class MyAggregateFunction implements AggregateFunction<Tuple3<String, String, Long>, Tuple3<String, Integer, Integer>, Tuple3<String, Double, String>> {
        @Override
        public Tuple3<String, Integer, Integer> createAccumulator() {
            return new Tuple3<>("", 0, 0);
        }

        @Override
        public Tuple3<String, Integer, Integer> add(Tuple3<String, String, Long> event, Tuple3<String, Integer, Integer> accumulator) {
            return new Tuple3<>(event.f0, accumulator.f1 + Integer.parseInt(event.f1), accumulator.f2 + 1);
        }

        @Override
        public Tuple3<String, Double, String> getResult(Tuple3<String, Integer, Integer> accumulator) {
            return new Tuple3<>(accumulator.f0, (double) accumulator.f1 / accumulator.f2, "元素个数：" + accumulator.f2);
        }

        @Override
        public Tuple3<String, Integer, Integer> merge(Tuple3<String, Integer, Integer> a, Tuple3<String, Integer, Integer> b) {
            return new Tuple3<>(a.f0, a.f1 + b.f1, a.f2 + b.f2);
        }
    }
}
