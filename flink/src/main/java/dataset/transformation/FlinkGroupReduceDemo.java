package dataset.transformation;

import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yidxue on 2018/2/12
 */
public class FlinkGroupReduceDemo {


    /**
     * 一次获得一个分组的全部数据
     */
    public static class DistinctReduce implements GroupReduceFunction<Tuple2<Integer, String>, Tuple2<Integer, String>> {
        @Override
        public void reduce(Iterable<Tuple2<Integer, String>> in, Collector<Tuple2<Integer, String>> out) {

            Set<String> uniqStrings = new HashSet<>();
            Integer key = null;

            // add all strings of the group to the set
            for (Tuple2<Integer, String> t : in) {
                key = t.f0;
                uniqStrings.add(t.f1);
            }

            // emit all unique strings.
            for (String s : uniqStrings) {
                out.collect(new Tuple2<>(key, s));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();


        DataSet<Tuple2<Integer, String>> inData = env.fromElements(
            Tuple2.of(1, "erwin"),
            Tuple2.of(1, "erwin"),
            Tuple2.of(2, "yuyi"),
            Tuple2.of(1, "win"));

        DataSet<Tuple2<Integer, String>> output = inData.groupBy(0).reduceGroup(new DistinctReduce());

        output.print();
    }
}
