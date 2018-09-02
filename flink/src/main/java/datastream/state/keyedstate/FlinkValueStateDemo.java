package datastream.state.keyedstate;

import datastream.state.keyedstate.service.ValueStateAverage1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author yidxue
 */
public class FlinkValueStateDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.fromElements(
            Tuple2.of("k1", 6L),
            Tuple2.of("k1", 4L),
            Tuple2.of("k2", 3L),
            Tuple2.of("k2", 5L),
            Tuple2.of("k3", 7L),
            Tuple2.of("k3", 7L),
            Tuple2.of("k2", 1L),
            Tuple2.of("k2", 2L))
            .keyBy(0)
            .flatMap(new ValueStateAverage1())
//            .flatMap(new CountWindowAverage2())
            .print();

        env.execute("state manage demo");
    }
}
