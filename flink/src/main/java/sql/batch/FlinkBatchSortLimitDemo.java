package sql.batch;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.types.Row;
import util.source.BatchCollectionSource;

/**
 * Created by yidxue on 2018/2/21
 */
public class FlinkBatchSortLimitDemo {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.createCollectionsEnvironment();
        BatchTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

        DataSet<Tuple3<String, Integer, Long>> input = env.fromCollection(BatchCollectionSource.getTupleSource()).map(
            new MapFunction<Tuple3<String, String, Long>, Tuple3<String, Integer, Long>>() {
                @Override
                public Tuple3<String, Integer, Long> map(Tuple3<String, String, Long> element) throws Exception {
                    return Tuple3.of(element.f0, Integer.parseInt(element.f1), element.f2);
                }
            }
        );
        tableEnv.registerDataSet("userInfo", input, "k, v, ts");

        // create a Table from a Table API query
        Table tapiResult = tableEnv.scan("userInfo").select("k, v, ts").orderBy("v").offset(1).fetch(3);
        tableEnv.toDataSet(tapiResult, Row.class).print();

        System.out.println("====================================");
        // create a Table from a SQL query
        // limit 必须和 order by 一起使用，否则报错
        Table sqlResult = tableEnv.sqlQuery("select k, v, ts from userInfo order by v desc limit 4");
        tableEnv.toDataSet(sqlResult, Row.class).print();
    }
}
