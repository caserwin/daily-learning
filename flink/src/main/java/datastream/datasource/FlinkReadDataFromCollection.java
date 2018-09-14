package datastream.datasource;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import java.util.ArrayList;

/**
 * Created by yidxue on 2018/2/8
 */
public class FlinkReadDataFromCollection {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        ArrayList<Tuple2<Integer, Integer>> arrayls = new ArrayList<>();
        arrayls.add(Tuple2.of(2, 6));
        arrayls.add(Tuple2.of(2, 4));
        arrayls.add(Tuple2.of(1, 3));
        arrayls.add(Tuple2.of(1, 5));

        DataStream<Tuple2<Integer, Integer>> dstream = env.fromCollection(arrayls);

        dstream.print();

        env.execute("run");
    }
}
