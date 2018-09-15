package sql.batch.function;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;

/**
 * Created by yidxue on 2018/2/21
 */
public class FlinkBatchTableFunctionDemo {

    public static class Split extends TableFunction<Tuple2<String, Integer>> {
        private String separator = " ";

        public Split(String separator) {
            this.separator = separator;
        }

        public void eval(String str) {
            for (String s : str.split(separator)) {
                collect(new Tuple2<>(s, s.length()));
            }
        }
    }


    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

        DataSet<String> input = env.fromElements("word1 w1", "word2 w2", "word3");

        // 1. Register the function.
        tableEnv.registerFunction("split", new Split("\\s+"));

        // 2. use the function in SQL API
        tableEnv.registerDataSet("test", input, "f0");
        Table table21 =tableEnv.sqlQuery("SELECT f0, word, length FROM test, LATERAL TABLE(split(f0)) as T(word, length)");
        Table table22 =tableEnv.sqlQuery("SELECT f0, word, length FROM test LEFT JOIN LATERAL TABLE(split(f0)) as T(word, length) ON TRUE");
        tableEnv.toDataSet(table21, Row.class).print();
        System.out.println("========================");
        tableEnv.toDataSet(table22, Row.class).print();

    }
}
