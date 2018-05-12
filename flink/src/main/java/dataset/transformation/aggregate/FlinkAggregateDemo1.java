package dataset.transformation.aggregate;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.tuple.Tuple4;
import static org.apache.flink.api.java.aggregation.Aggregations.*;

/**
 * Created by yidxue on 2018/5/12
 * @author yidxue
 */
public class FlinkAggregateDemo1 {

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Tuple4<Integer, Double, String, String>> inData = env.fromElements(
            Tuple4.of(2, 5.0, "erwin", "a"),
            Tuple4.of(2, 5.0, "erwin", "b"),
            Tuple4.of(1, 3.0, "erwin", "c"),
            Tuple4.of(1, 5.0, "ydx", "d"),
            Tuple4.of(6, 7.0, "win", "e"),
            Tuple4.of(6, 7.0, "er", "f")
        );


        AggregateOperator<Tuple4<Integer, Double, String, String>> output = inData
                                                                                // group DataSet on second field
                                                                                .groupBy(0)
                                                                                // compute sum of the first field
                                                                                .aggregate(SUM, 1)
                                                                                .and(MIN, 2)
                                                                                .and(MAX, 2);

        output.print();
    }
}
