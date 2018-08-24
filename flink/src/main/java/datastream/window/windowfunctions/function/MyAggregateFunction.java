package datastream.window.windowfunctions.function;

import bean.MyEvent;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * Created by yidxue on 2018/8/21
 * 窗口函数，用于求平均
 */
public class MyAggregateFunction implements AggregateFunction<MyEvent, Tuple2<Long, Long>, Double> {
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
