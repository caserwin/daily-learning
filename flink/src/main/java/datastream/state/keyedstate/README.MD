这里我实现了 ValueState、ListState、ReducingState、AggregatingState、MapState 这几个示例。
因为官网说 FoldingState 已经被标注过期了，所以我偷个懒不写了。

其中：
ValueState 是求平均值的示例。
ListState 是判断当前元素是否已经在历史出现的示例。
ReducingState 是求和示例。
AggregatingState 是求平均值示例。
MapState 是词频统计示例。写到这里的时候我就特别好奇，官网给的wordcount是怎么实现。


官网streaming首页的wordcount 示例。我把 timeWindow(Time.seconds(5)) 删了，实现的效果和我的FlinkMapStateDemo感觉差不多。
好奇内部实现原理。先mark下：
```
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

public class WindowWordCount {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple2<String, Integer>> dataStream = env
                .socketTextStream("localhost", 9999)
                .flatMap(new Splitter())
                .keyBy(0)
                .sum(1);

        dataStream.print();

        env.execute("Window WordCount");
    }

    public static class Splitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String sentence, Collector<Tuple2<String, Integer>> out) throws Exception {
            for (String word: sentence.split(" ")) {
                out.collect(new Tuple2<String, Integer>(word, 1));
            }
        }
    }
}
```

====================================================
大约2小时后、、、突然明白了一点，sum 这个方法其实执行的是：
```
aggregate(new SumAggregator<>(positionToSum, input.getType(), input.getExecutionConfig()));
```
而 SumAggregator 其实是一个 AggregationFunction 的实现类。所以我现在想到两点，需要记录：
1. AggregationFunction 不是必须跟在窗口函数之后吗？
2. 这个官方的 wordcount 是不是借助了AggregationFunction 中累加器的功能，实现类似state的效果？？