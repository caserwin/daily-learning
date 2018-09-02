package dataset.transformation;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.DataSetUtils;

/**
 * Created by yidxue on 2018/8/30
 */
public class FlinkZipElementDemo {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);
        DataSet<String> in = env.fromElements("A", "A", "B", "C", "D", "E", "F", "G", "H");
        DataSet<Tuple2<Long, String>> result1 = DataSetUtils.zipWithIndex(in);
        result1.print();

        System.out.println("====================");
        DataSet<Tuple2<Long, String>> result2 = DataSetUtils.zipWithUniqueId(in);
        result2.print();
    }
}
