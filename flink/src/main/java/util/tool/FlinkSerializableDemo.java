package util.tool;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import util.bean.SerializeBean;

/**
 * @author yidxue
 */
public class FlinkSerializableDemo {
    static SerializeBean sb = (SerializeBean) SerializeUtil.deSerializeFromFile("data/serializeBean.txt");

    public static class serializableFilter implements FilterFunction<Tuple2<String, Integer>> {
        @Override
        public boolean filter(Tuple2<String, Integer> in) {
            return sb.getSet().contains(in.f0);
        }
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple2<String, Integer>> dStream = env.fromElements(
            Tuple2.of("A", 6),
            Tuple2.of("A", 4),
            Tuple2.of("C", 3),
            Tuple2.of("B", 5),
            Tuple2.of("C", 7),
            Tuple2.of("B", 7),
            Tuple2.of("D", 17),
            Tuple2.of("D", 2));

        DataStream<Tuple2<String, Integer>> reStream = dStream.filter(new serializableFilter());
        reStream.print();

        // start run
        env.execute("Demo");
    }
}
