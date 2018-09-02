package datastream.state.keyedstate.service;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ReducingState;
import org.apache.flink.api.common.state.ReducingStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

/**
 * Created by yidxue on 2018/8/28
 */
public class ReducingStateSum extends RichFlatMapFunction<Tuple2<String, Long>, Tuple2<String, Long>> {

    private transient ReducingState<Tuple2<String, Long>> sum;

    public static class SumAndCount implements ReduceFunction<Tuple2<String, Long>> {
        @Override
        public Tuple2<String, Long> reduce(Tuple2<String, Long> v1, Tuple2<String, Long> v2) {
            return Tuple2.of(v1.f0, v1.f1 + v2.f1);
        }
    }

    @Override
    public void flatMap(Tuple2<String, Long> input, Collector<Tuple2<String, Long>> out) throws Exception {
        sum.add(input);
        Tuple2<String, Long> currentSum = sum.get();
        out.collect(new Tuple2<>(currentSum.f0, currentSum.f1));
    }

    @Override
    public void open(Configuration config) {
        ReducingStateDescriptor<Tuple2<String, Long>> descriptor =
            new ReducingStateDescriptor<>("average", new SumAndCount(), TypeInformation.of(new TypeHint<Tuple2<String, Long>>() {
            }));
        sum = getRuntimeContext().getReducingState(descriptor);
    }
}
