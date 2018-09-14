package sql.batch;

import util.bean.WCBean;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import util.source.BatchCollectionSource;

/**
 * @author yidxue
 */
public class FlinkBatchSelectTableDemo1 {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.createCollectionsEnvironment();
        BatchTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

        DataSet<WCBean> input = env.fromCollection(BatchCollectionSource.getBeanSource());
        Table table = tableEnv.fromDataSet(input);

        Table filtered = table
                             .groupBy("word")
                             .select("word, frequency.sum as frequency")
                             .filter("frequency = 3");

        DataSet<WCBean> result = tableEnv.toDataSet(filtered, WCBean.class);

        result.print();
    }
}
