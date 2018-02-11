package dataset.transformation;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * Created by yidxue on 2018/2/8
 */
public class FlinkMapDemo {

    public class IntAdder implements MapFunction<Tuple2<Integer, Integer>, Integer> {
        @Override
        public Integer map(Tuple2<Integer, Integer> in) {
            return in.f0 + in.f1;
        }
    }


    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Tuple2<Integer, Integer>> intPairs = env.fromElements(
            Tuple2.of(2, 6),
            Tuple2.of(2, 4),
            Tuple2.of(1, 3),
            Tuple2.of(1, 5),
            Tuple2.of(6, 7),
            Tuple2.of(6, 7),
            Tuple2.of(1, 17),
            Tuple2.of(1, 2));

        DataSet<Integer> intSums = intPairs.map(new FlinkMapDemo().new IntAdder());
        intSums.print();
    }
}
