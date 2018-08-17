package datastream.transformations;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by yidxue on 2018/5/14
 */
public class FlinkFilterDemo {

    public static class CusFilter implements FilterFunction<Integer> {
        @Override
        public boolean filter(Integer value) throws Exception {
            return value != 0;
        }
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Integer> dStream = env.fromElements(1, 2, 3, 0, 4, 0, 5, 6);
        DataStream<Integer> reStream = dStream.filter(new CusFilter());
        reStream.print();

        // start run
        env.execute("Demo");
    }
}
