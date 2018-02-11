package dataset.transformation;

import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * Created by yidxue on 2018/2/11
 */
public class FlinkCoGroupInnerJoinDemo {
    public static class InnerJoin implements CoGroupFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Tuple2<Integer, Integer>> {
        @Override
        public void coGroup(Iterable<Tuple2<Integer, String>> leftElements, Iterable<Tuple2<Integer, String>> rightElements, Collector<Tuple2<Integer, Integer>> out) {

            for (Tuple2<Integer, String> leftElem : leftElements) {
                for (Tuple2<Integer, String> rightElem : rightElements) {
                    out.collect(new Tuple2<>(leftElem.f0, rightElem.f0));
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSource<Integer> leftSide = env.fromElements(1, 2, 3, 4, 5);
        DataSet<Tuple2<Integer, String>> leftSide2 = leftSide.map(
            new MapFunction<Integer, Tuple2<Integer, String>>() {
                @Override
                public Tuple2<Integer, String> map(Integer integer) {
                    return new Tuple2<>(integer, "some data");
                }
            });

        DataSource<Integer> rightSide = env.fromElements(4, 5, 6, 7, 8, 9, 10);
        DataSet<Tuple2<Integer, String>> rightSide2 = rightSide.map(
            new MapFunction<Integer, Tuple2<Integer, String>>() {
                @Override
                public Tuple2<Integer, String> map(Integer integer) {
                    return new Tuple2<>(integer, "some other data");
                }
            });


        // where() 的参数是leftSide2 的字段索引，equalTo() 的参数是rightSide2 的字段索引。
        DataSet<Tuple2<Integer, Integer>> innerJoin = leftSide2.coGroup(rightSide2)
                                                          .where(0)
                                                          .equalTo(0)
                                                          .with(new InnerJoin());

        innerJoin.print();
    }
}
