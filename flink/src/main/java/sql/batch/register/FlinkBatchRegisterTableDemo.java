package sql.batch.register;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.types.Row;

/**
 * Created by yidxue on 2018/2/18
 */
public class FlinkBatchRegisterTableDemo {
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

        //
        Table tapiResult = tableEnv.scan("userInfo").select("*");
        tableEnv.toDataSet(tapiResult, Row.class).print();

        System.out.println("============================");
        //
        Table sqlResult = tableEnv.sqlQuery("select * from userInfo");
        tableEnv.toDataSet(sqlResult, Row.class).print();

    }
}
