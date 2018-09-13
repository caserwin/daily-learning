package datastream.datasource;

import util.source.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by yidxue on 2018/8/18
 *
 * @author yidxue
 */
public class FlinkAddSourceDemo {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置一个数据源，你们可以把1 改成 10 看看是什么效果。
        env.setParallelism(2);

        DataStream<Tuple3<String, String, Long>> stream = env.addSource(new DataSource()).name("Demo Source");
        stream.print();
        env.execute("addSourceDemo");
    }

}
