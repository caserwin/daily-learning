package datastream.transformations;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.util.Collector;
import java.util.HashSet;

/**
 * @author yidxue
 * 这个得多试运行。
 */
public class FlinkConnectDemo1 {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStream<Tuple3<String, String, Long>> source1 = env.addSource(new StreamDataSource1()).name("Demo Source").keyBy(1);
        DataStream<Tuple3<String, String, Long>> source2 = env.addSource(new StreamDataSource2()).name("Demo Source").keyBy(1);
        DataStream<Tuple4<String, String, Long, Integer>> result = source1.connect(source2).flatMap(new MyConnectedStreams());

        result.print();
        env.execute();
    }

    static final class MyConnectedStreams extends RichCoFlatMapFunction<Tuple3<String, String, Long>, Tuple3<String, String, Long>, Tuple4<String, String, Long, Integer>> {
        private ValueState<HashSet<String>> state = null;

        @Override
        public void open(Configuration config) {
            ValueStateDescriptor<HashSet<String>> descriptor = new ValueStateDescriptor<>("have-seen-key", TypeInformation.of(new TypeHint<HashSet<String>>() {
            }));
            state = getRuntimeContext().getState(descriptor);
        }

        @Override
        public void flatMap1(Tuple3<String, String, Long> source1, Collector<Tuple4<String, String, Long, Integer>> out) throws Exception {
            HashSet<String> set = state.value();
            if (set == null) {
                set = new HashSet<>();
                set.add(source1.f0);
                state.update(set);
            } else {
                set.add(source1.f0);
                state.update(set);
            }
        }

        @Override
        public void flatMap2(Tuple3<String, String, Long> source2, Collector<Tuple4<String, String, Long, Integer>> out) throws Exception {
            HashSet<String> set = state.value();
            if (set.contains(source2.f0)) {
                out.collect(Tuple4.of(source2.f0, source2.f1, source2.f2, set.size()));
            }
        }
    }

    public static class StreamDataSource1 extends RichParallelSourceFunction<Tuple3<String, String, Long>> {
        private volatile boolean running = true;

        @Override
        public void run(SourceContext<Tuple3<String, String, Long>> ctx) throws InterruptedException {

            Tuple3[] elements = new Tuple3[]{
                Tuple3.of("a", "beijing", 1000000052000L),
                Tuple3.of("c", "beijing", 1000000105000L),
                Tuple3.of("d", "beijing", 1000000106000L),
                Tuple3.of("b", "beijing", 1000000104000L),
            };

            int count = 0;
            while (running && count < elements.length) {
                ctx.collect(new Tuple3<>((String) elements[count].f0, (String) elements[count].f1, (long) elements[count].f2));
                count++;
                Thread.sleep(100);
            }
        }

        @Override
        public void cancel() {
            running = false;
        }
    }

    public static class StreamDataSource2 extends RichParallelSourceFunction<Tuple3<String, String, Long>> {
        private volatile boolean running = true;

        @Override
        public void run(SourceFunction.SourceContext<Tuple3<String, String, Long>> ctx) throws InterruptedException {

            Tuple3[] elements = new Tuple3[]{
                Tuple3.of("a", "beijing", 1000000058000L),
                Tuple3.of("c", "beijing", 1000000055000L),
                Tuple3.of("d", "beijing", 1000000106000L),
                Tuple3.of("e", "beijing", 1000000107000L),
            };

            int count = 0;
            while (running && count < elements.length) {
                ctx.collect(new Tuple3<>((String) elements[count].f0, (String) elements[count].f1, (long) elements[count].f2));
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