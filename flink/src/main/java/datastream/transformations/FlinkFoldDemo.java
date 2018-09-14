package datastream.transformations;

import org.apache.flink.api.common.functions.FoldFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by yidxue on 2018/5/14
 *
 * @author yidxue
 */
public class FlinkFoldDemo {
    public static class CusFold implements FoldFunction<Tuple2<Integer, Integer>, String> {
        @Override
        public String fold(String current, Tuple2<Integer, Integer> value) throws Exception {
            return current + "-(" + value.f0 + ":" + value.f1 + ")";
        }
    }

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

        DataStream<String> reStream = dStream.keyBy(0).fold("Start", new CusFold());
        reStream.print();

        // start run
        env.execute("Demo");
    }
}
