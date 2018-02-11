package dataset.transformation;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

/**
 * Created by yidxue on 2018/2/11
 */
public class FlinkProjectDemo {

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Tuple3<Integer, Double, String>> inData = env.fromElements(
            Tuple3.of(2, 6.0, "erwin"),
            Tuple3.of(2, 4.0, "erwin"),
            Tuple3.of(1, 3.0, "erwin"),
            Tuple3.of(1, 5.0, "ydx"),
            Tuple3.of(6, 7.0, "win"),
            Tuple3.of(6, 7.0, "er")
        );

        // 对于一个 Tuple3 类型的字段，保留指定index的field
        DataSet<Tuple2<String, Integer>> out = inData.project(2, 0);
        out.print();
    }
}
