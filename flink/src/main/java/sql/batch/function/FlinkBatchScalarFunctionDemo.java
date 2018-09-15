package sql.batch.function;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.table.functions.ScalarFunction;
import org.apache.flink.types.Row;

/**
 * Created by yidxue on 2018/2/20
 */
public class FlinkBatchScalarFunctionDemo {

    public static class Calculate extends ScalarFunction {
        private int factor = 100;

        public Calculate(int factor) {
            this.factor = factor;
        }

        public int eval(int a, int b) {
            return a * factor + b;
        }
    }

    public static class GetTime extends ScalarFunction {
        public long eval() {
            return System.currentTimeMillis();
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

        DataSet<Tuple3<String, Integer, Integer>> input = env.fromElements(
            Tuple3.of("a", 1, 1),
            Tuple3.of("a", 2, 1),
            Tuple3.of("a", 3, 1),
            Tuple3.of("b", 4, 1),
            Tuple3.of("b", 5, 1)
        );

        // 1. register the function
        tableEnv.registerFunction("calculate", new Calculate(10));
        tableEnv.registerFunction("getTime", new GetTime());

        // 2. use the function in Java Table API
        Table myTable = tableEnv.fromDataSet(input, "K,COL1,COL2");
        Table table1 = myTable.select("K, calculate(COL1,COL2),getTime()");
        tableEnv.toDataSet(table1, Row.class).print();

        System.out.println("========================");
        // 3. use the function in SQL API
        tableEnv.registerDataSet("test", input, "K,COL1,COL2");
        Table table2 = tableEnv.sqlQuery("SELECT K, calculate(COL1,COL2),getTime() FROM test");
        tableEnv.toDataSet(table2, Row.class).print();
    }
}
