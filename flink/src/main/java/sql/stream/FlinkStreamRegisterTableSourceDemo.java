package sql.stream;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.sources.CsvTableSource;
import org.apache.flink.table.sources.TableSource;

/**
 * Created by yidxue on 2018/2/18
 */
public class FlinkStreamRegisterTableSourceDemo {
    public static void main(String[] args){
        StreamExecutionEnvironment sEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment sTableEnv = TableEnvironment.getTableEnvironment(sEnv);

        // create a TableSource
//        TableSource csvSource = new CsvTableSource("/path/to/file", ...);

        // register the TableSource as table "CsvTable"
//        sTableEnv.registerTableSource("CsvTable", csvSource);


    }
}
