package sql.batch;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.table.sources.CsvTableSource;
import org.apache.flink.table.sources.TableSource;

/**
 * Created by yidxue on 2018/2/18
 */
public class FlinkBatchRegisterTableSourceDemo {
    public static void main(String[] args) {
        ExecutionEnvironment env = ExecutionEnvironment.createCollectionsEnvironment();
        BatchTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

        // create a TableSource
//        TableSource csvSource = new CsvTableSource("/path/to/file", ...);

        // register the TableSource as table "CsvTable"
//        tableEnv.registerTableSource("CsvTable", csvSource);


    }
}
