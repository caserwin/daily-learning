package datastream.timetype.processtime;

import bean.MyEvent;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by yidxue on 2018/8/18
 */
public class FlinkProcessTimeDemo1 {

    public static class StrToEventMap implements MapFunction<String, MyEvent> {
        @Override
        public MyEvent map(String in) {
            String[] tmp = in.trim().split("\\W+");

            return new MyEvent(Integer.parseInt(tmp[0]), tmp[1], 123456789);
        }
    }


    public static class SelectMess implements KeySelector<MyEvent, String> {
        @Override
        public String getKey(MyEvent w) {
            return w.getMessage();
        }
    }

    public static class MyAggregateFunction implements AggregateFunction<MyEvent, Tuple2<Long, Long>, Double> {
        @Override
        public Tuple2<Long, Long> createAccumulator() {
            return new Tuple2<>(0L, 0L);
        }

        @Override
        public Tuple2<Long, Long> add(MyEvent event, Tuple2<Long, Long> accumulator) {
            return new Tuple2<>(accumulator.f0 + event.getValue(), accumulator.f1 + 1L);
        }

        @Override
        public Double getResult(Tuple2<Long, Long> accumulator) {
            return ((double) accumulator.f0) / accumulator.f1;
        }

        @Override
        public Tuple2<Long, Long> merge(Tuple2<Long, Long> a, Tuple2<Long, Long> b) {
            return new Tuple2<>(a.f0 + b.f0, a.f1 + b.f1);
        }
    }

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置数据源
        DataStream<Double> source = env.socketTextStream("localhost", 9998)
                                        .map(new StrToEventMap())
                                        .keyBy(new SelectMess())
                                        .timeWindow(Time.seconds(10))
                                        .aggregate(new MyAggregateFunction());

        source.print();
        env.execute("TimeWindowDemo");
    }
}
