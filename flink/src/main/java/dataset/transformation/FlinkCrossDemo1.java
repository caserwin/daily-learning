package dataset.transformation;

import org.apache.flink.api.common.functions.CrossFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;
import util.bean.Coord;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by yidxue on 2019/3/16
 */
public class FlinkCrossDemo1 {

    public class EuclideanDistComputer implements CrossFunction<Coord, Coord, Tuple3<Integer, Integer, Double>> {

        @Override
        public Tuple3<Integer, Integer, Double> cross(Coord c1, Coord c2) {
            double dist = sqrt(pow(c1.getX() - c2.getX(), 2) + pow(c1.getY() - c2.getId(), 2));
            return new Tuple3<>(c1.getId(), c2.getId(), dist);
        }
    }


    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Coord> coords1 = env.fromElements(
            new Coord(1, 1, 2),
            new Coord(2, 2, 3)
        );

        DataSet<Coord> coords2 = env.fromElements(
            new Coord(3, 3, 4),
            new Coord(4, 4, 5)
        );

        DataSet<Tuple3<Integer, Integer, Double>> distances = coords1.cross(coords2).with(new FlinkCrossDemo1().new EuclideanDistComputer());

        distances.print();
    }
}
