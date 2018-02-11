package dataset.transformation.groupby;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * Created by yidxue on 2018/2/11
 */
public class FlinkGroupByIndexDemo {

    public static class WordCounterReduce implements ReduceFunction<Tuple2<String, Integer>> {
        @Override
        public Tuple2<String, Integer> reduce(Tuple2<String, Integer> in1, Tuple2<String, Integer> in2) {
            return new Tuple2<>(in1.f0, in1.f1 + in2.f1);
        }
    }

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Tuple2<String, Integer>> inData = env.fromElements(
            Tuple2.of("erwin", 5),
            Tuple2.of("erwin", 5),
            Tuple2.of("yuyi", 3));

        DataSet<Tuple2<String, Integer>> wordCounts = inData.groupBy(0).reduce(new WordCounterReduce());
        wordCounts.print();
    }
}
