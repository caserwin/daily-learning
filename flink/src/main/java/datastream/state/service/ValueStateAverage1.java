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
public class ValueStateAverage1 extends RichFlatMapFunction<Tuple2<String, Long>, Tuple2<String, Double>> {

    /**
     * The ValueState handle. The first field is the count, the second field a running sum.
     */
    private transient ValueState<Tuple2<Long, Long>> sum;

    @Override
    public void flatMap(Tuple2<String, Long> input, Collector<Tuple2<String, Double>> out) throws Exception {

        // access the state value
        Tuple2<Long, Long> currentSum = sum.value();

        if (currentSum != null) {
            // update the count
            currentSum.f0 += 1;

            // add the second field of the input value
            currentSum.f1 += input.f1;

            // update the state
            sum.update(currentSum);
        } else {
            sum.update(new Tuple2<>(1L, input.f1));
        }

        out.collect(new Tuple2<>(input.f0, (double) sum.value().f1 / sum.value().f0));
    }

    @Override
    public void open(Configuration config) {
        ValueStateDescriptor<Tuple2<Long, Long>> descriptor =
            new ValueStateDescriptor<>("average", TypeInformation.of(new TypeHint<Tuple2<Long, Long>>() {
            }));
        sum = getRuntimeContext().getState(descriptor);
    }
}