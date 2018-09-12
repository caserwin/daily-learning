package dataset.transformation.cogroup;

import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @author yidxue
 * code from: https://gist.github.com/mxm/c2e9c459a9d82c18d789
 */
public class FlinkCoGroupLeftJoinDemo {

    public static class LeftJoin implements CoGroupFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Tuple2<Integer, Integer>> {
        @Override
        public void coGroup(Iterable<Tuple2<Integer, String>> leftElements, Iterable<Tuple2<Integer, String>> rightElements, Collector<Tuple2<Integer, Integer>> out) {
            final int nullElement = -1;

            for (Tuple2<Integer, String> leftElem : leftElements) {
                boolean hadElements = false;
                for (Tuple2<Integer, String> rightElem : rightElements) {
                    out.collect(new Tuple2<>(leftElem.f0, rightElem.f0));
                    hadElements = true;
                }
                if (!hadElements) {
                    out.collect(new Tuple2<>(leftElem.f0, nullElement));
                }
            }
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

        DataSet<Tuple2<Integer, Integer>> leftOuterJoin = leftSide.coGroup(rightSide)
                                                              .where(0)
                                                              .equalTo(0)
                                                              .with(new LeftJoin());

        leftOuterJoin.print();
    }
}