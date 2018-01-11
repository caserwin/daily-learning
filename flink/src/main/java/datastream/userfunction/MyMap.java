package datastream.userfunction;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.types.Row;

/**
 * @author yidxue
 */
public class MyMap implements MapFunction<Tuple2<Boolean, Row>, String > {
    @Override
    public String map(Tuple2<Boolean, Row> in_value) {
        return in_value.getField(1).toString();
    }
}
