package datastream.transformations;

import bean.CustomType;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by yidxue on 2018/5/14
 * DataStream → KeyedStream
 */
public class FlinkKeyByDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple2<Integer, Integer>> dStream1 = env.fromElements(
            Tuple2.of(2, 6),
            Tuple2.of(2, 4),
            Tuple2.of(1, 3),
            Tuple2.of(1, 5),
            Tuple2.of(6, 7),
            Tuple2.of(6, 7),
            Tuple2.of(1, 17),
            Tuple2.of(1, 2));

        DataStream<CustomType> dStream2 = env.fromElements(
            new CustomType("name1", 1),
            new CustomType("name1", 1),
            new CustomType("name2", 2),
            new CustomType("name3", 2));

        dStream1.keyBy(0).print();
        dStream2.keyBy("aName").print();

        // start run
        env.execute("Demo");
    }
}
