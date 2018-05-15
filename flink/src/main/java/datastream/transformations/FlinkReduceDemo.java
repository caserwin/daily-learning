package datastream.transformations;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by yidxue on 2018/5/14
 *
 * @author yidxue
 */
public class FlinkReduceDemo {

    public static class CusReduce implements ReduceFunction<Tuple2<Integer, Integer>> {
        @Override
        public Tuple2<Integer, Integer> reduce(Tuple2<Integer, Integer> value1, Tuple2<Integer, Integer> value2) throws Exception {
            return Tuple2.of((value1.f0 + value2.f0) / 2, value1.f1 + value2.f1);
        }
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple2<Integer, Integer>> dStream = env.fromElements(
            Tuple2.of(2, 6),
            Tuple2.of(2, 4),
            Tuple2.of(1, 3),
            Tuple2.of(5, 7),
            Tuple2.of(5, 7),
            Tuple2.of(1, 17),
            Tuple2.of(1, 2));

        dStream.keyBy(0).reduce(new CusReduce()).print();
        // start run
        env.execute("Demo");
    }
}
