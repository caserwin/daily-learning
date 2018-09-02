package dataset.transformation.aggregate;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.util.Collector;
import java.util.Arrays;

/**
 * Created by yidxue on 2018/4/4
 */
public class FlinkAggregateDemo3 {
    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        String[] arrays = new String[]{"hello", "flink cluster", "hello"};
        DataSet<String> dataSet = env.fromCollection(Arrays.asList(arrays));
        DataSet<String> words = dataSet.flatMap(
            new FlatMapFunction<String, String>() {
                @Override
                public void flatMap(String value, Collector<String> out) {
                    for (String s : value.split("\\s+")) {
                        out.collect(s);
                    }
                }
            }
        );

        DataSet<Tuple2<String, Integer>> mappedWords = words.map(
            new MapFunction<String, Tuple2<String, Integer>>() {
                @Override
                public Tuple2<String, Integer> map(String word) {
                    return new Tuple2<>(word, 1);
                }
            }
        );

        DataSet<Tuple2<String, Integer>> grouped = mappedWords.groupBy(0).sum(1);

        grouped.collect().forEach(System.out::println);
    }
}
