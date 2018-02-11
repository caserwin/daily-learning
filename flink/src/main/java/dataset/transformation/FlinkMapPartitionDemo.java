package dataset.transformation;

import org.apache.flink.api.common.functions.MapPartitionFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * Created by yidxue on 2018/2/11
 */
public class FlinkMapPartitionDemo {

    public class PartitionCounter implements MapPartitionFunction<String, Long> {
        @Override
        public void mapPartition(Iterable<String> values, Collector<Long> out) {
            long c = 0;
            for (String s : values) {
                c++;
            }
            out.collect(c);
        }
    }

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // mapPartition 是按分区操作的，所以设置 setParallelism =3 调试
        DataSet<String> inData = env.fromElements("aa bb cc dd", "a bb cc ee", "a").setParallelism(3);
        DataSet<Long> counts = inData.flatMap(new FlinkFlatMapDemo().new Tokenizer()).mapPartition(new FlinkMapPartitionDemo().new PartitionCounter());

        counts.print();
    }
}
