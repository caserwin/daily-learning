package datastream.state.keyedstate.service;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import java.util.Iterator;

/**
 * Created by yidxue on 2018/8/29
 */
public class ListStateContain extends RichMapFunction<Tuple2<String, String>, String> {

    private transient ListState<String> list;

    @Override
    public String map(Tuple2<String, String> value) throws Exception {
        Iterator<String> it = list.get().iterator();
        boolean ifContain = false;
        while (it.hasNext()) {
            if (it.next().toLowerCase().equals(value.f1)) {
                ifContain = true;
                break;
            }
        }
        if (ifContain) {
            return "key = " + value.f0 + " value = " + value.f1 + " 重复出现";
        } else {
            list.add(value.f1);
            return "key = " + value.f0 + " value = " + value.f1;
        }
    }

    @Override
    public void open(Configuration config) {
        ListStateDescriptor<String> descriptor = new ListStateDescriptor<>("average", TypeInformation.of(new TypeHint<String>() {
        }));
        list = getRuntimeContext().getListState(descriptor);
    }
}
