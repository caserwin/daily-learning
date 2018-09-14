package sql.batch.function;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.table.functions.AggregateFunction;
import org.apache.flink.types.Row;

/**
 * Created by yidxue on 2018/2/21
 */
public class FlinkBatchAggregationFunctionDemo {

    public static class WeightedAvgAccum {
        public float sum = 0;
        public int count = 0;
    }

    /**
     * Weighted Average user-defined aggregate function.
     */
    public static class WeightedAvg extends AggregateFunction<Float, WeightedAvgAccum> {

        @Override
        public WeightedAvgAccum createAccumulator() {
            return new WeightedAvgAccum();
        }

        @Override
        public Float getValue(WeightedAvgAccum acc) {
            if (acc.count == 0) {
                return null;
            } else {
                return acc.sum / acc.count;
            }
        }

        public void accumulate(WeightedAvgAccum acc, float point, int value) {
            acc.sum += point * value;
            acc.count += value;
        }

        public void retract(WeightedAvgAccum acc, float point, int value) {
            acc.sum -= point * value;
            acc.count -= value;
        }

        public void merge(WeightedAvgAccum acc, Iterable<WeightedAvgAccum> it) {
            for (WeightedAvgAccum a : it) {
                acc.count += a.count;
                acc.sum += a.sum;
            }
        }

        public void resetAccumulator(WeightedAvgAccum acc) {
            acc.count = 0;
            acc.sum = 0L;
        }
    }


    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

        DataSet<Tuple3<String, Float, Integer>> input = env.fromElements(
            Tuple3.of("erw1", 0.5f, 2),
            Tuple3.of("erw2", 0.5f, 2),
            Tuple3.of("erw3", 0.3f, 1),
            Tuple3.of("erw1", 0.5f, 1),
            Tuple3.of("erw2", 0.7f, 6),
            Tuple3.of("erw1", 0.7f, 6));

        // 1. Register the function.
        tableEnv.registerFunction("wAvg", new WeightedAvg());

        // 2. use the function in SQL API: https://ci.apache.org/projects/flink/flink-docs-release-1.4/dev/table/sql.html
        tableEnv.registerDataSet("userScores", input, "user, point, level");
        Table table = tableEnv.sqlQuery("SELECT user, wAvg(point, level) AS avgPoints FROM userScores GROUP BY user");
        tableEnv.toDataSet(table, Row.class).print();
    }
}
