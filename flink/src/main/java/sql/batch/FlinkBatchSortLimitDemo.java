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
 * @author cisco
 */
public class FlinkBatchSortLimitDemo {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.createCollectionsEnvironment();
        BatchTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

        DataSet<Tuple3<String, Float, Integer>> input = env.fromElements(
            Tuple3.of("erw1", 0.5f, 2),
            Tuple3.of("erw2", 0.5f, 2),
            Tuple3.of("erw3", 0.3f, 1),
            Tuple3.of("erw1", 0.5f, 1),
            Tuple3.of("erw2", 0.7f, 6),
            Tuple3.of("erw1", 0.7f, 6));

        tableEnv.registerDataSet("userInfo", input, "name, point, level");

        // create a Table from a Table API query
        Table tapiResult = tableEnv.scan("userInfo").select("name, point, level").orderBy("level").offset(1).fetch(3);;
        tableEnv.toDataSet(tapiResult, Row.class).print();

        System.out.println("====================================");
        // create a Table from a SQL query
        // limit 必须和 order by 一起使用，否则报错
        Table sqlResult = tableEnv.sqlQuery("select name, point, level from userInfo order by level desc limit 4");
        tableEnv.toDataSet(sqlResult, Row.class).print();
    }
}
