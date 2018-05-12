package dataset.transformation.join;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * Created by yidxue on 2018/5/11
 * @author yidxue
 */
public class FlinkInnerJoinDemo2 {

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

    public static class PointWeighter implements JoinFunction<Rating, Tuple2<String, Double>, Tuple2<String, Double>> {
        @Override
        public Tuple2<String, Double> join(Rating rating, Tuple2<String, Double> weight) {
            return new Tuple2<>(rating.name, rating.points * weight.f1);
        }
    }

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Rating> ratings = env.fromElements(
            new FlinkInnerJoinDemo2.Rating("discoun1", 1),
            new FlinkInnerJoinDemo2.Rating("discoun2", 2),
            new FlinkInnerJoinDemo2.Rating("discoun3", 3),
            new FlinkInnerJoinDemo2.Rating("discoun4", 4));

        DataSet<Tuple2<String, Double>> weights = env.fromElements(
            Tuple2.of("discoun2", 0.1),
            Tuple2.of("discoun2", 0.2),
            Tuple2.of("discoun3", 0.3),
            Tuple2.of("discoun5", 0.4)
        );

        DataSet<Tuple2<String, Double>> weightedRatings = ratings.join(weights)
                                                              .where("name")
                                                              .equalTo("f0")
                                                              .with(new PointWeighter());
        weightedRatings.print();
    }
}
