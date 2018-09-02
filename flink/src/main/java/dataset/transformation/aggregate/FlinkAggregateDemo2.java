package dataset.transformation.aggregate;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.ReduceOperator;
import org.apache.flink.api.java.tuple.Tuple4;

/**
 * Created by yidxue on 2018/5/12
 */
public class FlinkAggregateDemo2 {

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Tuple4<Integer, Integer, Integer, Integer>> inData = env.fromElements(
            Tuple4.of(2, 5, 111, 3),
            Tuple4.of(2, 5, 222, 2),
            Tuple4.of(1, 3, 333, 1),
            Tuple4.of(1, 5, 444, 0),
            Tuple4.of(6, 7, 555, 9),
            Tuple4.of(6, 7, 333, 5)
        );


        ReduceOperator<Tuple4<Integer, Integer, Integer, Integer>> output = inData.groupBy(0).minBy(1, 2, 3);
        output.print();

        System.out.println("====================");
        ReduceOperator<Tuple4<Integer, Integer, Integer, Integer>> output1 = inData.maxBy(3);
        output1.print();
    }
}

