package sql.batch;

import util.bean.WCBean;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;

/**
 * @author yidxue
 */
public class FlinkBatchTableDemo {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.createCollectionsEnvironment();
        BatchTableEnvironment tEnv = TableEnvironment.getTableEnvironment(env);

        DataSet<WCBean> input = env.fromElements(
            new WCBean("Hello", 1),
            new WCBean("Ciao", 1),
            new WCBean("Hello", 1));

        Table table = tEnv.fromDataSet(input);

        Table filtered = table
                             .groupBy("word")
                             .select("word, frequency.sum as frequency")
                             .filter("frequency = 2");

        DataSet<WCBean> result = tEnv.toDataSet(filtered, WCBean.class);

        result.print();
    }
}
