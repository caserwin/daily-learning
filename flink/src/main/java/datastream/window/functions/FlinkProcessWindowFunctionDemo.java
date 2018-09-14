package datastream.window.functions;

import util.source.StreamDataSource;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * Created by yidxue on 2018/8/24
 */
public class FlinkProcessWindowFunctionDemo {

    public static void main(String[] args) throws Exception {
        int windowSize = 10;
        long delay = 5100L;

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
            .process(new MyProcessWindowFunction())
            .print();

        env.execute("FlinkProcessWindowFunctionDemo");
    }

    /**
     * 这个有点奇怪的，输入是TupleX 类型的， Key 一定是 Tuple 类型
     */
    public static class MyProcessWindowFunction extends ProcessWindowFunction<Tuple3<String, String, Long>, String, Tuple, TimeWindow> {
        @Override
        public void process(Tuple key, Context context, Iterable<Tuple3<String, String, Long>> input, Collector<String> out) {
            long count = 0;
            for (Tuple3<String, String, Long> in : input) {
                count++;
            }
            out.collect("key: " + key + "-> Window: " + context.window() + "-> count: " + count);
        }
    }
}
