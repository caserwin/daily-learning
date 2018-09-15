package sql.stream;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import util.source.StreamDataSource;

/**
 * Created by yidxue on 2018/9/14
 */
public class FlinkStreamRegisterTableDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);
        env.setParallelism(1);

        // method 1:
        DataStream<Tuple3<String, String, Long>> input1 = env.addSource(new StreamDataSource());
        Table out = tableEnv.fromDataStream(input1, "col1, col2, col3").select("col1, col2, col3");
        tableEnv.toAppendStream(out, Row.class).print();

        // method 2:
        tableEnv.registerDataStream("info", input1, "col1, col2, col3");
        Table sqlResult = tableEnv.sqlQuery("select count(*) from info");
        tableEnv.toRetractStream(sqlResult, Row.class).print();

        env.execute("Flink Table Demo");
    }
}