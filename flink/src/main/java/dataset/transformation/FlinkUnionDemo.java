package dataset.transformation;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.UnionOperator;
import org.apache.flink.api.java.tuple.Tuple3;

/**
 * Created by yidxue on 2018/2/11
 */
public class FlinkUnionDemo {
    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Tuple3<Integer, Double, String>> inData1 = env.fromElements(
            Tuple3.of(2, 5.0, "erwin"),
            Tuple3.of(2, 5.0, "erwin"),
            Tuple3.of(1, 3.0, "erwin")
        );


        DataSet<Tuple3<Integer, Double, String>> inData2 = env.fromElements(
            Tuple3.of(1, 5.0, "ydx"),
            Tuple3.of(6, 7.0, "win"),
            Tuple3.of(6, 7.0, "er")
        );

        // 数据类型必须一样，才能进行 Union 操作
        UnionOperator<Tuple3<Integer, Double, String>> outData = inData1.union(inData2);
        outData.print();
    }
}
