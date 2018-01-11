package datastream.state;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author yidxue
 */
public class Main {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.fromElements(
                Tuple2.of(2L, 6L),
                Tuple2.of(2L, 4L),
                Tuple2.of(1L, 3L),
                Tuple2.of(1L, 5L),
                Tuple2.of(6L, 7L),
                Tuple2.of(6L, 7L),
                Tuple2.of(1L, 17L),
                Tuple2.of(1L, 2L))
                .keyBy(0)
                .flatMap(new CountWindowAverage())
                .print();

        env.execute("gg");
    }
}
