package datastream.window.functions;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import util.source.StreamDataSource;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by yidxue on 2018/8/21
 */
public class FlinkAggregateFunctionDemo2 {

    public static void main(String[] args) throws Exception {
        int windowSize = 10;
        int sliding = 5;
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
                .window(SlidingEventTimeWindows.of(Time.seconds(windowSize), Time.seconds(sliding)))
                .aggregate(new MyAggregateFunction())
                .print();

        env.execute("FlinkWindowAggregateFunctionDemo");
    }

    public static class MyAggregateFunction implements AggregateFunction<Tuple3<String, String, Long>, Tuple2<String, ArrayList<Integer>>, Tuple2<String, String>> {
        @Override
        public Tuple2<String, ArrayList<Integer>> createAccumulator() {
            return new Tuple2<>("", new ArrayList<>());
        }

        @Override
        public Tuple2<String, ArrayList<Integer>> add(Tuple3<String, String, Long> event, Tuple2<String, ArrayList<Integer>> accumulator) {
            // TODO 注意这里，要不要深拷贝？
            accumulator.f1.add(Integer.parseInt(event.f1));
            return new Tuple2<>(event.f0, accumulator.f1);
        }

        @Override
        public Tuple2<String, String> getResult(Tuple2<String, ArrayList<Integer>> accumulator) {
            String value = accumulator.f1.stream().map(String::valueOf).collect(Collectors.joining(","));
            return new Tuple2<>(accumulator.f0, value);
        }

        @Override
        public Tuple2<String, ArrayList<Integer>> merge(Tuple2<String, ArrayList<Integer>> a, Tuple2<String, ArrayList<Integer>> b) {
            a.f1.addAll(b.f1);
            return new Tuple2<>(a.f0, a.f1);
        }
    }
}
