package dataset.transformation.join;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * Created by yidxue on 2018/5/11
 * @author yidxue
 */
public class FlinkLeftOuterJoinDemo {

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

    public static class OutJoinPointAssigner implements JoinFunction<Tuple2<String, Double>, Rating, Tuple2<String, Integer>> {
        @Override
        public Tuple2<String, Integer> join(Tuple2<String, Double> movie, Rating rating) {
            return new Tuple2<>(movie.f0, rating == null ? -1 : rating.points);
        }
    }

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Rating> ratings = env.fromElements(
            new FlinkLeftOuterJoinDemo.Rating("discoun1", 1),
            new FlinkLeftOuterJoinDemo.Rating("discoun2", 2),
            new FlinkLeftOuterJoinDemo.Rating("discoun3", 3),
            new FlinkLeftOuterJoinDemo.Rating("discoun4", 4));

        DataSet<Tuple2<String, Double>> weights = env.fromElements(
            Tuple2.of("discoun2", 0.1),
            Tuple2.of("discoun2", 0.2),
            Tuple2.of("discoun3", 0.3),
            Tuple2.of("discoun5", 0.4)
        );

        DataSet<Tuple2<String, Integer>> weightedRatings = weights.leftOuterJoin(ratings)
                                                               .where("f0")
                                                               .equalTo("name")
                                                               .with(new OutJoinPointAssigner());
        weightedRatings.print();
    }
}
