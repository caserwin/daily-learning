package datastream.state.service;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.AggregatingState;
import org.apache.flink.api.common.state.AggregatingStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;

/**
 * Created by yidxue on 2018/8/28
 */
public class AggregatingStateAverage extends RichMapFunction<Tuple2<String, Long>, Tuple2<String, Double>> {

    private transient AggregatingState<Tuple2<String, Long>, Tuple2<String, Double>> avg;

    @Override
    public Tuple2<String, Double> map(Tuple2<String, Long> value) throws Exception {
        avg.add(value);
        Tuple2<String, Double> res = avg.get();
        return new Tuple2<>(res.f0, res.f1);
    }

    @Override
    public void open(Configuration config) {
        AggregatingStateDescriptor<Tuple2<String, Long>, Tuple3<String, Long, Long>, Tuple2<String, Double>> descriptor =
            new AggregatingStateDescriptor<>("sum", new MyAggregateFunction(), TypeInformation.of(new TypeHint<Tuple3<String, Long, Long>>() {
            }));
        avg = getRuntimeContext().getAggregatingState(descriptor);
    }

    public class MyAggregateFunction implements AggregateFunction<Tuple2<String, Long>, Tuple3<String, Long, Long>, Tuple2<String, Double>> {
        @Override
        public Tuple3<String, Long, Long> createAccumulator() {
            return new Tuple3<>("", 0L, 0L);
        }

        @Override
        public Tuple3<String, Long, Long> add(Tuple2<String, Long> value, Tuple3<String, Long, Long> accumulator) {
            return new Tuple3<>(value.f0, accumulator.f1 + value.f1, accumulator.f2 + 1L);
        }

        @Override
        public Tuple2<String, Double> getResult(Tuple3<String, Long, Long> accumulator) {
            return Tuple2.of(accumulator.f0, ((double) accumulator.f1) / accumulator.f2);
        }

        @Override
        public Tuple3<String, Long, Long> merge(Tuple3<String, Long, Long> a, Tuple3<String, Long, Long> b) {
            return new Tuple3<>(a.f0, a.f1 + b.f1, a.f2 + b.f2);
        }
    }
}
