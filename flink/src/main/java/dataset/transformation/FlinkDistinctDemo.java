package dataset.transformation;

import bean.CustomType;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DistinctOperator;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

/**
 * Created by yidxue on 2018/2/11
 */
public class FlinkDistinctDemo {

    public class CustomMap implements MapFunction<CustomType, Tuple2<String, Integer>> {
        @Override
        public Tuple2<String, Integer> map(CustomType ct) {
            return Tuple2.of(ct.aName, ct.aNumber);
        }
    }

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Tuple3<Integer, Double, String>> inData = env.fromElements(
            Tuple3.of(2, 5.0, "erwin"),
            Tuple3.of(2, 5.0, "erwin"),
            Tuple3.of(1, 3.0, "erwin"),
            Tuple3.of(1, 5.0, "ydx"),
            Tuple3.of(6, 7.0, "win"),
            Tuple3.of(6, 7.0, "er")
        );

        // distinct 集合元素
        DistinctOperator<Tuple> out = inData.project(2, 0).distinct();
        out.print();

        // 根据指定index field 进行 distinct，保留第一次出现的元素
        DistinctOperator<Tuple> out1 = inData.project(2, 0).distinct(0);
        out1.print();

        // Distinct with key expression, * 表示所有字段
        DataSet<CustomType> input = env.fromElements(
            new CustomType("name1", 1),
            new CustomType("name1", 1),
            new CustomType("name2", 2),
            new CustomType("name3", 2));
        // DataSet<CustomType> out2 = input.distinct("*");
        DataSet<CustomType> out2 = input.distinct("aNumber");
        out2.map(new FlinkDistinctDemo().new CustomMap()).print();
    }
}
