package dataset.transformation.cogroup;

import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by yidxue on 2018/2/11
 */
public class FlinkCoGroupOuterJoinDemo {

    public static class OuterJoin implements CoGroupFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Tuple3<Integer, String, String>> {
        @Override
        public void coGroup(Iterable<Tuple2<Integer, String>> leftElements, Iterable<Tuple2<Integer, String>> rightElements, Collector<Tuple3<Integer, String, String>> out) {
            HashMap<Integer, String> left = new HashMap<>();
            HashMap<Integer, String> right = new HashMap<>();
            HashSet<Integer> set = new HashSet<>();

            for (Tuple2<Integer, String> leftElem : leftElements) {
                set.add(leftElem.f0);
                left.put(leftElem.f0, leftElem.f1);
            }

            for (Tuple2<Integer, String> rightElem : rightElements) {
                set.add(rightElem.f0);
                right.put(rightElem.f0, rightElem.f1);
            }

            for (Integer key : set) {
                out.collect(new Tuple3<>(key, getHashMapByDefault(left, key, "null"), getHashMapByDefault(right, key, "null")));
            }
        }

        private String getHashMapByDefault(HashMap<Integer, String> map, Integer key, String defaultValue) {
            return map.get(key) == null ? defaultValue : map.get(key);
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSource<Tuple2<Integer, String>> leftSide = env.fromElements(
            Tuple2.of(1, "left"),
            Tuple2.of(2, "left"),
            Tuple2.of(3, "left"),
            Tuple2.of(4, "left"),
            Tuple2.of(5, "left")
        );

        DataSource<Tuple2<Integer, String>> rightSide = env.fromElements(
            Tuple2.of(4, "right"),
            Tuple2.of(5, "right"),
            Tuple2.of(6, "right"),
            Tuple2.of(7, "right"),
            Tuple2.of(8, "right"),
            Tuple2.of(9, "right")
        );

        DataSet<Tuple3<Integer, String, String>> leftOuterJoin = leftSide
                                                                     .coGroup(rightSide)
                                                                     .where(0)
                                                                     .equalTo(0)
                                                                     .with(new OuterJoin());

        leftOuterJoin.print();
    }
}
