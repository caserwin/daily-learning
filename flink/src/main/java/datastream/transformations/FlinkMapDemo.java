package datastream.transformations;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by yidxue on 2018/5/14
 * DataStream → DataStream
 * @author yidxue
 */
public class FlinkMapDemo {

    public static class IntAdder implements MapFunction<Tuple2<Integer, Integer>, Integer> {
        @Override
        public Integer map(Tuple2<Integer, Integer> in) {
            return in.f0 + in.f1;
        }
    }


    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple2<Integer, Integer>> dStream = env.fromElements(
            Tuple2.of(2, 6),
            Tuple2.of(2, 4),
            Tuple2.of(1, 3),
            Tuple2.of(1, 5),
            Tuple2.of(6, 7),
            Tuple2.of(6, 7),
            Tuple2.of(1, 17),
            Tuple2.of(1, 2));

        DataStream<Integer> reStream = dStream.map(new IntAdder());
        reStream.print();

        // start run
        env.execute("Demo");
    }
}
