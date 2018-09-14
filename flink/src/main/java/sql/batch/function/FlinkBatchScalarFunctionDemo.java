package sql.batch.function;

import util.bean.WCBean;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.table.functions.ScalarFunction;
import org.apache.flink.types.Row;
import util.source.BatchCollectionSource;

/**
 * Created by yidxue on 2018/2/20
 */
public class FlinkBatchScalarFunctionDemo {

    public static class HashCode extends ScalarFunction {
        private int factor = 12;

        public HashCode(int factor) {
            this.factor = factor;
        }

        public int eval(String s, int frequency) {
            return s.hashCode() * factor + frequency;
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

        DataSet<WCBean> input = env.fromCollection(BatchCollectionSource.getBeanSource());

        // 1. register the function
        tableEnv.registerFunction("hashCode", new HashCode(10));

        // 2. use the function in Java Table API
        Table myTable = tableEnv.fromDataSet(input);
        Table table1 = myTable.select("word, hashCode(word,frequency)");
        tableEnv.toDataSet(table1, Row.class).print();

        System.out.println("========================");
        // 3. use the function in SQL API
        tableEnv.registerDataSet("WordCount", input, "word, frequency");
        Table table2 = tableEnv.sqlQuery("SELECT word, hashCode(word,frequency) FROM WordCount");
        tableEnv.toDataSet(table2, Row.class).print();
    }
}
