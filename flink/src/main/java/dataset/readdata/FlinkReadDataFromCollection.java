package dataset.readdata;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

/**
 * @author yidxue
 */
public class FlinkReadDataFromCollection {

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSet<String> ds1 = env.fromElements("a","b","c","d");
        ds1.print();
    }
}