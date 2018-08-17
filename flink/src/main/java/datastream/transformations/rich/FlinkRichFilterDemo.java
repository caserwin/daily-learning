package datastream.transformations.rich;

import datastream.transformations.FlinkFilterDemo;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author yiduxe
 */
public class FlinkRichFilterDemo {

    public class MyFilter extends RichFilterFunction<Integer> {
        @Override
        public void open(Configuration parameters) {
            // Call loadLibrary()
        }

        @Override
        public boolean filter(Integer value) {
            return value != 0;
        }
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Integer> dStream = env.fromElements(1, 2, 3, 0, 4, 0, 5, 6);
        DataStream<Integer> reStream = dStream.filter(new FlinkFilterDemo.CusFilter());
        reStream.print();

        // start run
        env.execute("Demo");
    }
}
