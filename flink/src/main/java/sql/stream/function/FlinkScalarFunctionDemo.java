package sql.stream.function;

import org.apache.flink.types.Row;
import util.bean.WCBean;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.functions.ScalarFunction;
import util.source.StreamDataSource;

/**
 * Created by yidxue on 2018/2/20
 */
public class FlinkScalarFunctionDemo {

    public static class HashCode extends ScalarFunction {
        private int factor = 12;

        public HashCode(int factor) {
            this.factor = factor;
        }

        public int eval(String s) {
            return s.hashCode() * factor;
        }
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);
        env.setParallelism(1);

        DataStream<WCBean> input = env.fromElements(
            new WCBean("Hello", 1),
            new WCBean("Ciao", 1),
            new WCBean("Hello", 1));
        Table myTable = tableEnv.fromDataStream(input);

        // 1. register the function
        tableEnv.registerFunction("hashCode", new HashCode(10));

        // 2. use the function in Java Table API
        Table table1 = myTable.select("word, word.hashCode(), hashCode(word)");
        tableEnv.toAppendStream(table1, Row.class).print();

        // 3. use the function in SQL API
        tableEnv.registerDataStream("WordCount", input, "word, frequency");
        Table table2 = tableEnv.sqlQuery("SELECT word, hashCode(word) FROM WordCount");
        tableEnv.toAppendStream(table2, Row.class).print();

        env.execute("Flink Table Demo");
    }
}
