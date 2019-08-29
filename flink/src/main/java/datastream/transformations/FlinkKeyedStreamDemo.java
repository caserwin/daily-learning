package datastream.transformations;

import util.bean.Element;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by yidxue on 2018/5/14
 * DataStream → KeyedStream
 */
public class FlinkKeyedStreamDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple2<Integer, Integer>> dStream1 = env.fromElements(
                Tuple2.of(2, 6),
                Tuple2.of(2, 4),
                Tuple2.of(1, 3),
                Tuple2.of(1, 5),
                Tuple2.of(6, 7),
                Tuple2.of(6, 7),
                Tuple2.of(1, 17),
                Tuple2.of(1, 2));

        DataStream<Element> dStream2 = env.fromElements(
                new Element("name1", 1),
                new Element("name1", 1),
                new Element("name2", 2),
                new Element("name3", 2));

        // 根据第一个字段分成 3 组
        KeyedStream<Tuple2<Integer, Integer>, Tuple> kstream1 = dStream1.keyBy(0);

        // 根据 aName 分成4组
        KeyedStream<Element, Tuple> kstream2 = dStream2.keyBy("name");

        // 根据奇数和偶数分成两组
        KeyedStream<Tuple2<Integer, Integer>, Integer> kstream3 = dStream1.keyBy(new KeySelector<Tuple2<Integer, Integer>, Integer>() {
            @Override
            public Integer getKey(Tuple2<Integer, Integer> value) {
                return value.f0 % 2;
            }
        });

        KeyedStream<Tuple2<Integer, Integer>, Integer> kstream4 = dStream1.keyBy(new myKeySelector());

//        kstream1.print();
//        kstream2.print();
        kstream3.print();
        kstream4.print();

        // start run
        env.execute("Demo");
    }

    private static class myKeySelector implements KeySelector<Tuple2<Integer, Integer>, Integer> {

        @Override
        public Integer getKey(Tuple2<Integer, Integer> value) {
            return value.f0 % 2;
        }
    }
}
