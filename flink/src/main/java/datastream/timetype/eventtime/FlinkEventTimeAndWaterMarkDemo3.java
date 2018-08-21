//package datastream.watermark;
//
//import bean.WCBean;
//import org.apache.flink.api.common.functions.FilterFunction;
//import org.apache.flink.api.java.functions.KeySelector;
//import org.apache.flink.api.java.tuple.Tuple;
//import org.apache.flink.streaming.api.TimeCharacteristic;
//import org.apache.flink.streaming.api.datastream.DataStream;
//import org.apache.flink.streaming.api.datastream.KeyedStream;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
//import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
//import org.apache.flink.streaming.api.windowing.time.Time;
//
///**
// * Created by yidxue on 2018/8/20
// *
// * @author yidxue
// */
//public class FlinkEventTimeAndWaterMarkDemo3 {
//
//    public static class CusFilter implements FilterFunction<MyEvent> {
//        @Override
//        public boolean filter(MyEvent value) throws Exception {
//            return value.getValue() % 2 == 0;
//        }
//    }
//
//    public static class SelectMess implements KeySelector<MyEvent, String> {
//        @Override
//        public String getKey(MyEvent w) {
//            return w.getMessage();
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
//
//        // 设置数据源
//        DataStream<MyEvent> stream = env.fromElements(
//            new MyEvent(1, "a", 1534581000),
//            new MyEvent(2, "b", 1534581005),
//            new MyEvent(8, "a", 1534581015),
//            new MyEvent(4, "b", 1534580000),
//            new MyEvent(8, "a", 1534580000)
//        ).setParallelism(1);
//
//        // 校验数据，一般有filter
//        DataStream<MyEvent> filteredStream = stream.filter(new FlinkEventTimeAndWaterMarkDemo3.CusFilter());
//
//        // 设置了一个1分钟的Watermarks，表示最多等待1分钟(为了不无限的等待下去)，业务发生时间超过系统时间1分钟的数据都不进行统计。
//        DataStream<MyEvent> watermarkStream = filteredStream.assignTimestampsAndWatermarks(
//            new BoundedOutOfOrdernessTimestampExtractor<MyEvent>(Time.seconds(10)) {
//                @Override
//                public long extractTimestamp(MyEvent element) {
//                    return element.getTimestamp();
//                }
//            }
//        );
//
//        // 数据分组
//        KeyedStream<MyEvent, String> keyedStream = stream.keyBy(new SelectMess());
//
//        // 指定时间窗口、聚合计算
//        keyedStream.window(TumblingEventTimeWindows.of(Time.seconds(10))).apply().setParallelism(1).print();
//
//        env.execute("WindowWordCount");
//    }
//}
