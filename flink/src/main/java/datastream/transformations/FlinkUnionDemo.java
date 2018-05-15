package datastream.transformations;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by yidxue on 2018/5/15
 */
public class FlinkUnionDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple2<Integer, Integer>> dStream1 = env.fromElements(
            Tuple2.of(1, 1),
            Tuple2.of(1, 3),
            Tuple2.of(1, 5),
            Tuple2.of(1, 7)
        );

        DataStream<Tuple2<Integer, Integer>> dStream2 = env.fromElements(
            Tuple2.of(2, 2),
            Tuple2.of(2, 4),
            Tuple2.of(2, 6),
            Tuple2.of(2, 8)
        );

        dStream1.union(dStream2).print();

        // start run
        env.execute("Demo");
    }
}
