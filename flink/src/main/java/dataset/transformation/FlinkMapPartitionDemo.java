package dataset.transformation;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapPartitionFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * Created by yidxue on 2018/2/11
 */
public class FlinkMapPartitionDemo {

    public class PartitionCounter implements MapPartitionFunction<String, String> {
        @Override
        public void mapPartition(Iterable<String> values, Collector<String> out) {
            long c = 0;
            StringBuilder eles = new StringBuilder();
            for (String s : values) {
                c++;
                eles.append(s).append(" ");
            }
            out.collect(eles + ": " + c);
        }
    }

    public static class Tokenizer implements FlatMapFunction<String, String> {
        @Override
        public void flatMap(String value, Collector<String> out) {
            for (String token : value.split("\\s+")) {
                out.collect(token);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // mapPartition 是按分区操作的，所以设置 setParallelism =3 调试，可以多设置几个数字进行调试。
        DataSet<String> inData = env.fromElements("aa bb cc dd", "a bb cc ee", "a").setParallelism(3);
        DataSet<String> counts = inData.flatMap(new Tokenizer()).mapPartition(new FlinkMapPartitionDemo().new PartitionCounter());

        counts.print();
    }
}
