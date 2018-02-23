package sql.batch.register;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;

/**
 * Created by yidxue on 2018/2/18
 */
public class FlinkBatchRegisterTableSinkDemo {
    public static void main(String[] args){
        ExecutionEnvironment env = ExecutionEnvironment.createCollectionsEnvironment();
        BatchTableEnvironment tEnv = TableEnvironment.getTableEnvironment(env);
    }
}
