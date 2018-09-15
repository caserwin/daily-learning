package sql.batch;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import util.bean.WCBean;
import util.source.BatchCollectionSource;

/**
 * @author yidxue
 */
public class FlinkBatchSQLDemo {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tEnv = TableEnvironment.getTableEnvironment(env);

        DataSet<WCBean> input = env.fromCollection(BatchCollectionSource.getBeanSource());
        tEnv.registerDataSet("WordCount", input, "word, frequency");

        Table table = tEnv.sqlQuery("SELECT word, SUM(frequency) as frequency FROM WordCount GROUP BY word");

        DataSet<WCBean> result = tEnv.toDataSet(table, WCBean.class);
        result.print();
    }
}
