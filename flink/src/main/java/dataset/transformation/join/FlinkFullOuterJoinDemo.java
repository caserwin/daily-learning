package dataset.transformation.join;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

/**
 * Created by yidxue on 2018/5/11
 *
 * 这个 outer join 必须左右两边去重的
 */
public class FlinkFullOuterJoinDemo {

    public static class Rating {
        public String name;
        public int points;

        public Rating() {
        }

        public Rating(String name, int points) {
            this.name = name;
            this.points = points;
        }
    }

    public static class OutJoinPointAssigner implements JoinFunction<Tuple2<String, Double>, Rating, Tuple3<String, Double, Integer>> {
        @Override
        public Tuple3<String, Double, Integer> join(Tuple2<String, Double> weight, Rating rating) {
            if (weight != null) {
                return new Tuple3<>(weight.f0, weight.f1, rating == null ? -1 : rating.points);
            } else {
                return new Tuple3<>(rating.name, -1.0, rating.points);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Rating> ratings = env.fromElements(
            new Rating("discoun1", 1),
            new Rating("discoun2", 2),
            new Rating("discoun3", 3),
            new Rating("discoun4", 4));

        DataSet<Tuple2<String, Double>> weights = env.fromElements(
            Tuple2.of("discoun2", 0.2),
            Tuple2.of("discoun3", 0.3),
            Tuple2.of("discoun5", 0.4)
        );

        DataSet<Tuple3<String, Double, Integer>> weightedRatings = weights
                                                                       .fullOuterJoin(ratings)
                                                                       .where("f0")
                                                                       .equalTo("name")
                                                                       .with(new OutJoinPointAssigner());

        weightedRatings.print();
    }
}

