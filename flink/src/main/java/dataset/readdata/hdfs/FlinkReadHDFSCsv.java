package dataset.readdata.hdfs;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;

/**
 * Created by yidxue on 2018/2/17
 */
public class FlinkReadHDFSCsv {

    private static final String SAP_PATH = "hdfs://10.29.42.40:8020/user/pda/jmtForCall/jmtdata/jmtdata_AllSites_2018-01-01.csv";

    public static void main(String[] args) throws Exception {

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSet<Tuple3<Integer, String, Double>> csvInput = env.readCsvFile(SAP_PATH).types(Integer.class, String.class, Double.class);
        csvInput.print();

    }
}
