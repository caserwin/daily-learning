package sql.stream.function;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.functions.ScalarFunction;
import org.apache.flink.types.Row;
import sql.batch.function.FlinkBatchScalarFunctionDemo;

/**
 * Created by yidxue on 2018/2/20
 */
public class FlinkStreamScalarFunctionDemo {

    public static class HashCode extends ScalarFunction {
        private int factor = 12;

        public HashCode(int factor) {
            this.factor = factor;
        }

        public int eval(String s) {
            return s.hashCode() * factor;
        }
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);
        env.setParallelism(1);

        DataStream<Tuple3<String, Integer, Integer>> input = env.fromElements(
            Tuple3.of("a", 1, 1),
            Tuple3.of("a", 2, 1),
            Tuple3.of("a", 3, 1),
            Tuple3.of("b", 4, 1),
            Tuple3.of("b", 5, 1)
        );

        // 1. register the function
        tableEnv.registerFunction("calculate", new FlinkBatchScalarFunctionDemo.Calculate(10));
        tableEnv.registerFunction("getTime", new FlinkBatchScalarFunctionDemo.GetTime());

        // 2. use the function in Java Table API
        Table myTable = tableEnv.fromDataStream(input, "K,COL1,COL2");
        Table table1 = myTable.select("K, calculate(COL1,COL2),getTime()");
        tableEnv.toAppendStream(table1, Row.class).print();

        // 3. use the function in SQL API
        tableEnv.registerDataStream("test", input, "K,COL1,COL2");
        Table table2 = tableEnv.sqlQuery("SELECT K, calculate(COL1,COL2),getTime() FROM test");
        tableEnv.toAppendStream(table2, Row.class).print();

        env.execute("Flink Table Demo");
    }
}
