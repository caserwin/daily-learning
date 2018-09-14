package sql.batch;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.types.Row;

/**
 * Created by yidxue on 2018/2/23
 */
public class FlinkBatchSelectTableDemo2 {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tEnv = TableEnvironment.getTableEnvironment(env);

        DataSet<Tuple2<String, Integer>> input = env.fromElements(
            Tuple2.of("Hello", 1),
            Tuple2.of("Ciao", 1),
            Tuple2.of("Hello", 1));

        // register the DataSet as table "WordCount"
        tEnv.registerDataSet("WordCount", input,"word, count");
        tEnv.scan("WordCount").printSchema();

        // where() and fliter() will get the same result
        Table table = tEnv.scan("WordCount")
                          .as("word, frequent")
                          .select("*")
                          .where("word === 'Hello'");

        table.printSchema();
        tEnv.toDataSet(table, Row.class).print();
    }
}
