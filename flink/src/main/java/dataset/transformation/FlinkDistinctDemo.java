package dataset.transformation;

import util.bean.Element;
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

    public class CustomMap implements MapFunction<Element, Tuple2<String, Long>> {
        @Override
        public Tuple2<String, Long> map(Element ct) {
            return Tuple2.of(ct.getName(), ct.getNumber());
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
        System.out.println("===================");
        // 根据指定index field 进行 distinct，保留第一次出现的元素
        DistinctOperator<Tuple> out1 = inData.project(2, 0).distinct(0);
        out1.print();
        System.out.println("===================");
        // Distinct with key expression, * 表示所有字段
        DataSet<Element> input = env.fromElements(
            new Element("name1", 1),
            new Element("name1", 1),
            new Element("name2", 2),
            new Element("name3", 2));
        // DataSet<CustomType> out2 = input.distinct("*");
        DataSet<Element> out2 = input.distinct("number");
        out2.map(new FlinkDistinctDemo().new CustomMap()).print();
    }
}
