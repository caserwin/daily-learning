package datastream.state;

import datastream.state.service.ListStateContain;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by yidxue on 2018/8/29
 */
public class FlinkListStateDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.fromElements(
            Tuple2.of("k1", "a"),
            Tuple2.of("k1", "a"),
            Tuple2.of("k2", "b"),
            Tuple2.of("k2", "c"),
            Tuple2.of("k3", "c"),
            Tuple2.of("k3", "a"),
            Tuple2.of("k2", "b"),
            Tuple2.of("k2", "c"))
            .keyBy(0)
            .map(new ListStateContain())
            .print();

        env.execute("state manage demo");
    }
}
