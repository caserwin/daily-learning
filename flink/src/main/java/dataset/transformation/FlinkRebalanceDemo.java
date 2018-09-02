package dataset.transformation;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapPartitionFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * Created by yidxue on 2018/2/11
 */
public class FlinkRebalanceDemo {
    public static class PartitionCounter implements MapPartitionFunction<String, Long> {
        @Override
        public void mapPartition(Iterable<String> values, Collector<Long> out) {
            long c = 0;
            for (String s : values) {
                c++;
            }
            out.collect(c);
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
        // 并行度默认是 10
        env.setParallelism(3);

        // mapPartition 是按分区操作的，所以设置 setParallelism =3 调试
        DataSet<String> inData = env.fromElements("aa bb cc dd", "a bb cc ee", "a").setParallelism(3);
        DataSet<Long> counts = inData
                                   .flatMap(new Tokenizer())
                                   .mapPartition(new PartitionCounter());
        counts.print();

        DataSet<Long> countsRebalances = inData
                                             .flatMap(new Tokenizer())
                                             .rebalance()
                                             .mapPartition(new PartitionCounter());
        countsRebalances.print();
    }
}
