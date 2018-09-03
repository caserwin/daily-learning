package dataset.readdata.hdfs;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;

/**
 * @author yidxue
 */
public class FlinkReadHDFSCsv {

    private static final String SAP_PATH = "hdfs://localhost:9000/user/tmp.csv";

    public static void main(String[] args) throws Exception {

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSet<Tuple3<Integer, String, Integer>> csvInput = env.readCsvFile(SAP_PATH).types(Integer.class, String.class, Integer.class);
        csvInput.print();
    }
}
