package datastream.datasource;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by yidxue on 2018/8/22
 * nc -lk 9990
 */
public class FlinkSocketSourceDemo {
    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置数据源
        DataStream<String> source = env.socketTextStream("localhost", 9990);
        source.print();
        env.execute("FlinkSocketSource");
    }
}
