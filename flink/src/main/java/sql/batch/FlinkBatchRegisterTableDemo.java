package sql.batch;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.types.Row;
import util.bean.WCBean;
import util.source.BatchCollectionSource;

/**
 * Created by yidxue on 2018/2/21
 */
public class FlinkBatchRegisterTableDemo {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.createCollectionsEnvironment();
        BatchTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

        DataSet<Tuple3<String, String, Long>> input1 = env.fromCollection(BatchCollectionSource.getTupleSource());
        DataSet<WCBean> input2 = env.fromCollection(BatchCollectionSource.getBeanSource());

        // method 1: Table API 使用，tuple to Table
        Table in1 = tableEnv.fromDataSet(input1, "col1, col2, col3").select("col1,col2");
        tableEnv.toDataSet(in1, Row.class).print();

        // method 2: Table API 使用，Pojo to Table
        Table in2 = tableEnv.fromDataSet(input2).select("word,frequency");
        tableEnv.toDataSet(in2, Row.class).print();

        // method 3: SQL API 的使用
        tableEnv.registerDataSet("userInfo", input1, "name, point, level");
        Table sqlResult = tableEnv.sqlQuery("select * from userInfo");
        tableEnv.toDataSet(sqlResult, Row.class).print();

    }
}
