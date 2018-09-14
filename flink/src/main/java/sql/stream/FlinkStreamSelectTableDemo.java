package sql.stream;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import util.source.StreamDataSource;

/**
 * Created by yidxue on 2018/2/18
 */
public class FlinkStreamSelectTableDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment stableEnv = TableEnvironment.getTableEnvironment(env);
        env.setParallelism(1);

        DataStreamSource<Tuple3<String, String, Long>> input = env.addSource(new StreamDataSource());
        Table table = stableEnv.fromDataStream(input, "col1,col2,col3");
        Table filtered = table.groupBy("col1").select("col1, col2.cast(INT).sum as col2");

        DataStream<Tuple2<Boolean, Row>> result = stableEnv.toRetractStream(filtered, Row.class);
        result.print();
        env.execute("Flink Table Demo");
    }
}
