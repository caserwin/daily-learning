package datastream.window.join;

import datastream.datasource.DataSource;
import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by yidxue on 2018/9/12
 */
public class FlinkTumblingWindowsOuterJoinDemo {
    public static void main(String[] args) throws Exception {
        int windowSize = 10;
        long delay = 5100L;

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);

        // 设置数据源
        DataStream<Tuple3<String, String, Long>> leftSource = env.addSource(new DataSource()).name("Demo Source");
        DataStream<Tuple3<String, String, Long>> rightSource = env.addSource(new DataSource1()).name("Demo Source");

        // 设置水位线
        DataStream<Tuple3<String, String, Long>> leftStream = leftSource.assignTimestampsAndWatermarks(
            new BoundedOutOfOrdernessTimestampExtractor<Tuple3<String, String, Long>>(Time.milliseconds(delay)) {
                @Override
                public long extractTimestamp(Tuple3<String, String, Long> element) {
                    return element.f2;
                }
            }
        );

        DataStream<Tuple3<String, String, Long>> rigjhtStream = rightSource.assignTimestampsAndWatermarks(
            new BoundedOutOfOrdernessTimestampExtractor<Tuple3<String, String, Long>>(Time.milliseconds(delay)) {
                @Override
                public long extractTimestamp(Tuple3<String, String, Long> element) {
                    return element.f2;
                }
            }
        );

        // join 操作
        leftStream.coGroup(rigjhtStream)
            .where(new LeftSelectKey()).equalTo(new RightSelectKey())
            .window(TumblingEventTimeWindows.of(Time.seconds(windowSize)))
            .apply(new OuterJoin())
            .print();


        env.execute("TimeWindowDemo");
    }

    public static class OuterJoin implements CoGroupFunction<Tuple3<String, String, Long>, Tuple3<String, String, Long>, Tuple5<String, String, String, Long, Long>> {
        @Override
        public void coGroup(Iterable<Tuple3<String, String, Long>> leftElements, Iterable<Tuple3<String, String, Long>> rightElements, Collector<Tuple5<String, String, String, Long, Long>> out) {
            HashMap<String, Element> left = new HashMap<>();
            HashMap<String, Element> right = new HashMap<>();
            HashSet<String> set = new HashSet<>();

            for (Tuple3<String, String, Long> leftElem : leftElements) {
                set.add(leftElem.f0);
                left.put(leftElem.f0, new Element(leftElem.f1, leftElem.f2));
            }

            for (Tuple3<String, String, Long> rightElem : rightElements) {
                System.out.println(rightElem.f0);
                set.add(rightElem.f0);
                right.put(rightElem.f0, new Element(rightElem.f1, rightElem.f2));
            }

            for (String key : set) {
                Element leftElem = getHashMapByDefault(left, key, new Element("null", -1L));
                Element rightElem = getHashMapByDefault(right, key, new Element("null", -1L));

                out.collect(new Tuple5<>(key, leftElem.getF1(), rightElem.getF1(), leftElem.getF2(), rightElem.getF2()));
            }
        }

        private Element getHashMapByDefault(HashMap<String, Element> map, String key, Element defaultValue) {
            return map.get(key) == null ? defaultValue : map.get(key);
        }

    }

    public static class Element {
        private String f1;
        private Long f2;

        public Element(String f1, Long f2) {
            this.f1 = f1;
            this.f2 = f2;
        }

        public String getF1() {
            return f1;
        }

        public Long getF2() {
            return f2;
        }
    }

    public static class LeftSelectKey implements KeySelector<Tuple3<String, String, Long>, String> {
        @Override
        public String getKey(Tuple3<String, String, Long> w) {
            return w.f0;
        }
    }

    public static class RightSelectKey implements KeySelector<Tuple3<String, String, Long>, String> {
        @Override
        public String getKey(Tuple3<String, String, Long> w) {
            return w.f0;
        }
    }
}
