package sql.batch;

import util.bean.WCBean;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import util.source.BatchCollectionSource;

/**
 * Created by yidxue on 2018/2/23
 */
public class FlinkBatchSelectTableDemo2 {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tEnv = TableEnvironment.getTableEnvironment(env);

        DataSet<WCBean> input = env.fromCollection(BatchCollectionSource.getBeanSource());
        tEnv.registerDataSet("WordCount", input, "word, frequency");

        Table table = tEnv
                          .scan("WordCount")
                          .as("word, frequency")
                          .select("*")
                          .where("word === 'Hello'");

        tEnv.toDataSet(table, WCBean.class).print();
        table.printSchema();
    }
}
