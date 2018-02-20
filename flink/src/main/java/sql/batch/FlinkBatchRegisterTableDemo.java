package sql.batch;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;

/**
 * Created by yidxue on 2018/2/18
 */
public class FlinkBatchRegisterTableDemo {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.createCollectionsEnvironment();
        BatchTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

//        tableEnv.registerTable("table1", ...)            // or

// create a Table from a Table API query
        Table tapiResult = tableEnv.scan("table1").select("");
// create a Table from a SQL query
        Table sqlResult  = tableEnv.sqlQuery("SELECT ... FROM table2 ... ");

// emit a Table API result Table to a TableSink, same for SQL result
//        tapiResult.writeToSink(...);

// execute
        env.execute();


    }
}
