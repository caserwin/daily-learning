package sql.stream;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;

/**
 * Created by yidxue on 2018/2/20
 */
public class FlinkStreamConvertToTableDemo {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

        DataStream<Tuple2<Integer, Integer>> stream = env.fromElements(
            Tuple2.of(2, 6),
            Tuple2.of(2, 4),
            Tuple2.of(1, 3),
            Tuple2.of(1, 5),
            Tuple2.of(6, 7),
            Tuple2.of(6, 7),
            Tuple2.of(1, 17),
            Tuple2.of(1, 2));

// Convert the DataStream into a Table with default fields "f0", "f1"
        Table table1 = tableEnv.fromDataStream(stream);

// Convert the DataStream into a Table with fields "myLong", "myString"
        Table table2 = tableEnv.fromDataStream(stream, "myLong, myString");
    }
}
