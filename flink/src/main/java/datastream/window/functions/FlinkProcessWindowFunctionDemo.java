package datastream.window.functions;

import bean.MyEvent;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * Created by yidxue on 2018/8/24
 */
public class FlinkProcessWindowFunctionDemo {

    public static void main(String[] args) throws Exception {
        int windowSize = 10;
        long delay = 5100L;

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);

        // 设置数据源
        DataStream<MyEvent> source = env.addSource(new DataSource()).name("Demo Source");

        // 设置水位线
        DataStream<MyEvent> stream = source.assignTimestampsAndWatermarks(
            new BoundedOutOfOrdernessTimestampExtractor<MyEvent>(Time.milliseconds(delay)) {
                @Override
                public long extractTimestamp(MyEvent element) {
                    return element.getTimestamp();
                }
            }
        );

        // 窗口聚合
        stream
            .keyBy(new SelectMess())
            .timeWindow(Time.seconds(windowSize))
            .process(new MyProcessWindowFunction())
            .print();

        env.execute("FlinkProcessWindowFunctionDemo");
    }

    public static class SelectMess implements KeySelector<MyEvent, String> {
        @Override
        public String getKey(MyEvent w) {
            return w.getMessage();
        }
    }

    public static class MyProcessWindowFunction extends ProcessWindowFunction<MyEvent, String, String, TimeWindow> {
        @Override
        public void process(String key, Context context, Iterable<MyEvent> input, Collector<String> out) {
            long count = 0;
            for (MyEvent in : input) {
                count++;
            }
            out.collect("key: " + key + "-> Window: " + context.window() + "-> count: " + count);
        }
    }


    public static class DataSource extends RichParallelSourceFunction<MyEvent> {
        private volatile boolean running = true;

        @Override
        public void run(SourceContext<MyEvent> ctx) throws InterruptedException {

            Tuple3[] elements = new Tuple3[]{
                Tuple3.of("a", 1, 1000000050000L),
                Tuple3.of("a", 2, 1000000054000L),
                Tuple3.of("a", 3, 1000000079900L),
                Tuple3.of("a", 4, 1000000115000L),
                Tuple3.of("b", 5, 1000000100000L),
                Tuple3.of("b", 6, 1000000108000L)
            };

            int count = 0;
            while (running && count < elements.length) {
                ctx.collect(new MyEvent((int) elements[count].f1, elements[count].f0.toString(), (long) elements[count].f2));
                count++;
                Thread.sleep(1000);
            }
        }

        @Override
        public void cancel() {
            running = false;
        }
    }
}
