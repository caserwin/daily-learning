package dataset.transformation.reduce;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

/**
 * Created by yidxue on 2018/2/11
 */
public class FlinkReduceDemo1 {
    public static class NumberReduce implements ReduceFunction<Integer> {
        @Override
        public Integer reduce(Integer a, Integer b) {
            return a + b;
        }
    }

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Integer> inData = env.fromElements(1, 2, 3, 4, 5, 6);

        DataSet<Integer> wordCounts = inData.reduce(new NumberReduce());
        wordCounts.print();
    }
}
