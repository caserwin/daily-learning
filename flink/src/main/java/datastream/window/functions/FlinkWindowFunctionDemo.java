package datastream.window.functions;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import java.util.Random;

/**
 * Created by yidxue on 2018/8/23
 * 参考：https://blog.csdn.net/whr_yy/article/details/79887275
 */
public class FlinkWindowFunctionDemo {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Tuple2<String, String>> redisDS = env.addSource(new SourceFunction<Tuple2<String, String>>() {
            @Override
            public void run(SourceContext<Tuple2<String, String>> ctx) throws Exception {
                Random random = new Random();
                while (true) {
                    ctx.collect(new Tuple2<>("1", "A"));
                    Thread.sleep(1000);
                }
            }

            @Override
            public void cancel() {
            }
        });
        DataStreamSource<Tuple2<String, String>> redisDSB = env.addSource(new SourceFunction<Tuple2<String, String>>() {
            @Override
            public void run(SourceContext<Tuple2<String, String>> ctx) throws Exception {
                while (true) {
                    ctx.collect(new Tuple2<>("2", "B"));
                    Thread.sleep(1000);
                }
            }

            @Override
            public void cancel() {
            }
        });
        DataStreamSource<Tuple2<String, String>> redisDSC = env.addSource(new SourceFunction<Tuple2<String, String>>() {
            @Override
            public void run(SourceContext<Tuple2<String, String>> ctx) throws Exception {
                while (true) {
                    ctx.collect(new Tuple2<>("3", "C"));
                    Thread.sleep(1000);
                }
            }

            @Override
            public void cancel() {
            }
        });

        DataStream<Tuple2<String, String>> union = redisDS.union(redisDSB).union(redisDSC);
        KeyedStream<Tuple2<String, String>, Integer> tuple2IntegerKeyedStream = union.keyBy(new KeySelector<Tuple2<String, String>, Integer>() {
            @Override
            public Integer getKey(Tuple2<String, String> value) {
                // 这里把原来的 stream 分成两组，结果为0 的一组，结果为1 的一组。
                return Integer.parseInt(value.f0) % 2;
            }
        });

        tuple2IntegerKeyedStream.timeWindow(Time.seconds(3)).apply(new WindowFunction<Tuple2<String, String>, String, Integer, TimeWindow>() {
            @Override
            public void apply(Integer integer, TimeWindow window, Iterable<Tuple2<String, String>> input, Collector<String> out) {
                StringBuffer stringBuffer = new StringBuffer();
                input.forEach(t -> {
                    stringBuffer.append(t.toString()).append("  ");
                });
                System.out.println(stringBuffer.toString());
            }
        });

        env.execute("WindowsFunctionDemo");
    }
}
