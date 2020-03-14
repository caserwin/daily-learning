package datastream.transformations;

import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import util.bean.Coord;

/**
 * @author yidxue
 */
public class FlinkLambdaTypeDemo {
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



        DataStream<Tuple2<String, Coord>> reStream = dStream.map(x -> Tuple2.of(x.f0.toString(), new Coord(x.f1,x.f1,x.f1)))
                .returns(Types.TUPLE(Types.STRING, TypeInformation.of(new TypeHint<Coord>() {})));
        reStream.print();

        // start run
        env.execute("Demo");
    }
}
