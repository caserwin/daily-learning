package datastream.window.functions;

import datastream.datasource.DataSource;
import org.apache.flink.api.common.functions.FoldFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by yidxue on 2018/8/22
 */
public class FlinkFoldFunctionDemo {

    public static void main(String[] args) throws Exception {
        int windowSize = 10;
        long delay = 5000L;

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

        // 聚合函数
        stream
            .keyBy(0)
            .window(TumblingEventTimeWindows.of(Time.seconds(windowSize)))
            .fold(Tuple2.of("", ""), new FoldFunction<Tuple3<String, String, Long>, Tuple2<String, String>>() {
                @Override
                public Tuple2<String, String> fold(Tuple2<String, String> accumulator, Tuple3<String, String, Long> event) {
                    return new Tuple2<>(event.f0, accumulator.f1 + event.f1);
                }
            })
            .print();

        env.execute("FlinkWindowFoldFunctionDemo");
    }
}
