package datastream.datasource;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by yidxue on 2018/2/8
 */
public class FlinkReadDataFromCollection {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple2<Integer, Integer>> dstream = env.fromElements(
            Tuple2.of(2, 6),
            Tuple2.of(2, 4),
            Tuple2.of(1, 3),
            Tuple2.of(1, 5),
            Tuple2.of(6, 7),
            Tuple2.of(6, 7),
            Tuple2.of(1, 17),
            Tuple2.of(1, 2));

        dstream.print();

        env.execute("run");
    }
}
