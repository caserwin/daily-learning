package sql.stream;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;

/**
 * Created by yidxue on 2018/2/18
 */
public class FlinkStreamRegisterTableDemo {
    public static void main(String[] args) {
        StreamExecutionEnvironment sEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment sTableEnv = TableEnvironment.getTableEnvironment(sEnv);



    }
}
