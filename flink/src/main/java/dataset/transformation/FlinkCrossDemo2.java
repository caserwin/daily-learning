package dataset.transformation;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;

/**
 * Created by yidxue on 2019/3/16
 */
public class FlinkCrossDemo2 {

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Tuple3<Integer, Double, String>> coords1 = env.fromElements(
            Tuple3.of(1, 6.0, "erwin"),
            Tuple3.of(2, 7.0, "erwin"),
            Tuple3.of(3, 8.0, "win")
        );

        DataSet<Tuple3<Integer, Double, String>> coords2 = env.fromElements(
            Tuple3.of(2, 5.0, "erwin"),
            Tuple3.of(3, 3.0, "erwin"),
            Tuple3.of(4, 5.0, "ydx")
        );

        DataSet<Tuple3<Integer, Integer, Double>> distances = coords1
                                                                  .cross(coords2)
                                                                  .projectFirst(0, 1)
                                                                  .projectSecond(0)
                                                                  .projectSecond(2);

        distances.print();
    }
}
