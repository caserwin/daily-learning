package dataset.transformation.aggregate;

import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import java.util.HashMap;

/**
 * Created by yidxue on 2018/2/12
 */
public class FlinkGroupReduceFunctionDemo {

    /**
     * 一次获得一个分组的全部数据
     */
    public static class DistinctReduce implements GroupReduceFunction<Tuple2<Integer, String>, Tuple2<Integer, String>> {
        @Override
        public void reduce(Iterable<Tuple2<Integer, String>> in, Collector<Tuple2<Integer, String>> out) {

            HashMap<String, Integer> uniqStrings = new HashMap<>();

            // add all strings of the group to the set
            for (Tuple2<Integer, String> t : in) {
                uniqStrings.put(t.f1, t.f0);
            }

            // emit all unique strings.
            for (String key : uniqStrings.keySet()) {
                out.collect(new Tuple2<>(uniqStrings.get(key), key));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Tuple2<Integer, String>> inData = env.fromElements(
            Tuple2.of(1, "erwin"),
            Tuple2.of(1, "erwin"),
            Tuple2.of(2, "yuyi"),
            Tuple2.of(2, "erwin"),
            Tuple2.of(1, "win"));

        DataSet<Tuple2<Integer, String>> output = inData.groupBy(0).reduceGroup(new DistinctReduce());
//        DataSet<Tuple2<Integer, String>> output = inData.reduceGroup(new DistinctReduce());

        output.print();
    }
}
