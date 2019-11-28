package datastream.transformations;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by yidxue on 2018/5/15
 * @author yidxue
 */
public class FlinkAggregationsDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple2<Integer, Integer>> dStream = env.fromElements(
                Tuple2.of(2, 6),
                Tuple2.of(2, 4),
                Tuple2.of(1, 3),
                Tuple2.of(1, 5),
                Tuple2.of(6, 7),
                Tuple2.of(6, 7),
                Tuple2.of(1, 17),
                Tuple2.of(1, 2));

//        DataStream<Tuple2<Integer,Integer>> sumStream = dStream.keyBy(0).sum(1);
//        sumStream.print();

//        DataStream<Tuple2<Integer, Integer>> minStream = dStream.keyBy(0).min(1);
//        minStream.print();

//        DataStream<Tuple2<Integer,Integer>> maxStream = dStream.keyBy(0).max(1);
//        maxStream.print();

        DataStream<Tuple2<Integer,Integer>> minByStream = dStream.keyBy(0).minBy(1);
        minByStream.print();

//        DataStream<Tuple2<Integer,Integer>> maxByStream = dStream.keyBy(0).maxBy(1);
//        maxByStream.print();

        // start run
        env.execute("Demo");
    }
}
