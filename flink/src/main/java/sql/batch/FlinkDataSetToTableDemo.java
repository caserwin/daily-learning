package sql.batch;

import util.bean.WCBean;
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
public class FlinkDataSetToTableDemo {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.createCollectionsEnvironment();
        BatchTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

        DataSet<Tuple3<String, Float, Integer>> input1 = env.fromElements(
            Tuple3.of("erw1", 0.5f, 2),
            Tuple3.of("erw2", 0.5f, 2),
            Tuple3.of("erw3", 0.3f, 1),
            Tuple3.of("erw1", 0.5f, 1),
            Tuple3.of("erw2", 0.7f, 6),
            Tuple3.of("erw1", 0.7f, 6));

        // method 1: tuple to Table
        Table in1 = tableEnv.fromDataSet(input1, "a, b, c");
        tableEnv.toDataSet(in1, Row.class).print();

        System.out.println("==========================================");

        // method 2: Pojo to Table
        DataSet<WCBean> input2 = env.fromElements(
            new WCBean("Hello", 1),
            new WCBean("Ciao", 1),
            new WCBean("Hello", 1));

        Table in2 = tableEnv.fromDataSet(input2);
        tableEnv.toDataSet(in2, Row.class).print();
        System.out.println("==========================================");

        // method 3:
        tableEnv.registerDataSet("userInfo", input1, "name, point, level");
        Table sqlResult = tableEnv.sqlQuery("select * from userInfo");
        tableEnv.toDataSet(sqlResult, Row.class).print();

    }
}
