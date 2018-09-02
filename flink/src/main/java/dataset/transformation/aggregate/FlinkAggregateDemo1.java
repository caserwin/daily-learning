package dataset.transformation.aggregate;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.tuple.Tuple4;

import static org.apache.flink.api.java.aggregation.Aggregations.*;

/**
 * Created by yidxue on 2018/5/12
 */
public class FlinkAggregateDemo1 {

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

        AggregateOperator<Tuple4<Integer, Integer, Integer, Integer>> output = inData
                                                                                   .groupBy(0)
                                                                                   .aggregate(SUM, 1)
                                                                                   .and(MIN, 2)
                                                                                   .and(MAX, 3);
        output.print();
    }
}
