package sql.batch;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.types.Row;

/**
 * Created by yidxue on 2018/2/21
 */
public class FlinkBatchSortLimitDemo {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.createCollectionsEnvironment();
        BatchTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

        DataSet<Tuple3<String, Integer, Long>> input = env.fromElements(
            Tuple3.of("a", 1, 1000000050000L),
            Tuple3.of("a", 2, 1000000054000L),
            Tuple3.of("a", 3, 1000000079900L),
            Tuple3.of("a", 4, 1000000115000L),
            Tuple3.of("b", 5, 1000000100000L),
            Tuple3.of("b", 6, 1000000108000L)
        );

        tableEnv.registerDataSet("userInfo", input, "k, v, ts");

        // create a Table from a Table API query
        Table tapiResult = tableEnv.scan("userInfo").select("k, v, ts").orderBy("v").offset(1).fetch(3);
        tableEnv.toDataSet(tapiResult, Row.class).print();

        System.out.println("====================================");
        // create a Table from a SQL query
        Table sqlResult = tableEnv.sqlQuery("select k, v, ts from userInfo limit 4");
        tableEnv.toDataSet(sqlResult, Row.class).print();
    }
}
