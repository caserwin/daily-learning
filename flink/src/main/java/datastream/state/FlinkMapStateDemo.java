package datastream.state;

import datastream.state.service.MapStateAverage;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by yidxue on 2018/8/29
 */
public class FlinkMapStateDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.fromElements(
            Tuple2.of("k1", "a"),
            Tuple2.of("k1", "b"),
            Tuple2.of("k2", "a"),
            Tuple2.of("k2", "a"),
            Tuple2.of("k3", "c"),
            Tuple2.of("k3", "c"),
            Tuple2.of("k2", "a"),
            Tuple2.of("k2", "b"))
            .keyBy(0)
            .map(new MapStateAverage())
            .print();

        env.execute("state manage demo");
    }
}
