package dataset.transformation;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

/**
 * Created by yidxue on 2018/2/11
 */
public class FlinkFilterDemo {

    /**
     * FilterFunction that filters out all Integers smaller than zero.
     */
    public class NumberFilter implements FilterFunction<Integer> {
        @Override
        public boolean filter(Integer number) {
            return number >= 3;
        }
    }

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Integer> inData = env.fromElements(1, 2, 3, 4, 5, 4, 3, 2, 1);
        DataSet<Integer> intSums = inData.filter(new FlinkFilterDemo().new NumberFilter());
        intSums.print();
    }
}
