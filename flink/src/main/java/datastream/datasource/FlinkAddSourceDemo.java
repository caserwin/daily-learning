package datastream.datasource;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

/**
 * Created by yidxue on 2018/8/18
 *
 * @author yidxue
 */
public class FlinkAddSourceDemo {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置一个数据源，你们可以把1 改成 10 看看是什么效果。
        env.setParallelism(2);

        DataStream<Tuple2<Integer, String>> stream = env.addSource(new DataSource()).name("Demo Source");
        stream.print();
        env.execute("addSourceDemo");
    }

    private static class DataSource extends RichParallelSourceFunction<Tuple2<Integer, String>> {
        private volatile boolean running = true;

        @Override
        public void run(SourceContext<Tuple2<Integer, String>> ctx) throws Exception {
            Tuple2[] elements = new Tuple2[]{
                Tuple2.of(2, "1534581525"),
                Tuple2.of(2, "1534581515"),
                Tuple2.of(1, "1534581505"),
                Tuple2.of(1, "1534581425"),
                Tuple2.of(6, "1534581325"),
                Tuple2.of(3, "1534581325"),
                Tuple2.of(4, "1534581125"),
                Tuple2.of(5, "1534581025")
            };

            int count = 0;
            while (running && count < elements.length) {
                ctx.collect(elements[count]);
                count++;
            }
        }

        @Override
        public void cancel() {
            running = false;
        }
    }
}
