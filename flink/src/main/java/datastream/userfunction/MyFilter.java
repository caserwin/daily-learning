package datastream.userfunction;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.types.Row;

/**
 * @author yidxue
 */
public class MyFilter implements FilterFunction<Tuple2<Boolean, Row>> {
    @Override
    public boolean filter(Tuple2<Boolean, Row> value) {
        return value.getField(0);
    }
}
