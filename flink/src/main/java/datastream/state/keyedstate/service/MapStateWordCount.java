package datastream.state.keyedstate.service;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;

/**
 * Created by yidxue on 2018/8/29
 */
public class MapStateWordCount extends RichMapFunction<Tuple2<String, String>, Tuple3<String, String, Long>> {

    private transient MapState<String, Long> map;

    @Override
    public Tuple3<String, String, Long> map(Tuple2<String, String> value) throws Exception {
        if (!map.contains(value.f1)) {
            map.put(value.f1, 1L);
        } else {
            map.put(value.f1, map.get(value.f1) + 1);
        }
        return new Tuple3<>(value.f0, value.f1, map.get(value.f1));
    }

    @Override
    public void open(Configuration config) {
        MapStateDescriptor<String, Long> descriptor =
            new MapStateDescriptor<>("average", TypeInformation.of(new TypeHint<String>() {
            }), TypeInformation.of(new TypeHint<Long>() {
            }));
        map = getRuntimeContext().getMapState(descriptor);
    }
}
