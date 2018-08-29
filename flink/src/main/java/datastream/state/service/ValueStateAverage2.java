package datastream.state.service;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

/**
 * @author yidxue
 * 全局状态记录统计
 */
public class ValueStateAverage2 extends RichFlatMapFunction<Tuple2<String, Long>, Tuple2<String, Long>> {

    /**
     * The ValueState handle. The first field is the count, the second field a running sum.
     */
    private transient ValueState<Tuple2<Long, Long>> sum;

    @Override
    public void flatMap(Tuple2<String, Long> input, Collector<Tuple2<String, Long>> out) throws Exception {

        // access the state value
        Tuple2<Long, Long> currentSum = sum.value();

        // update the count
        currentSum.f0 += 1;

        // add the second field of the input value
        currentSum.f1 += input.f1;

        // update the state
        sum.update(currentSum);

        out.collect(new Tuple2<>(input.f0, currentSum.f1 / currentSum.f0));
    }

    @Override
    public void open(Configuration config) {
        ValueStateDescriptor<Tuple2<Long, Long>> descriptor =
            new ValueStateDescriptor<>(
                // the state name
                "average",
                // type information
                TypeInformation.of(new TypeHint<Tuple2<Long, Long>>() {
                }),
                // default value of the state, if nothing was set
                Tuple2.of(0L, 0L));
        sum = getRuntimeContext().getState(descriptor);
    }
}