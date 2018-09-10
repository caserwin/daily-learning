package datastream.timetype.eventtime.predefined;

import bean.MyEvent;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;

/**
 * Created by yidxue on 2018/8/19
 */
public class AscendingTimestampExtractorDemo {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);

        DataStream<MyEvent> stream = env.fromElements(
            new MyEvent(1, "a", 1000000050000L),
            new MyEvent(1, "a", 1000000052000L),
            new MyEvent(3, "c", 1000000079900L),
            new MyEvent(2, "b", 1000000054000L),
            new MyEvent(4, "d", 1000000115000L)
        );

        DataStream<MyEvent> withTimestampsAndWatermarks =
            stream.assignTimestampsAndWatermarks(new AscendingTimestampExtractor<MyEvent>() {
                @Override
                public long extractAscendingTimestamp(MyEvent element) {
                    System.out.println("watermark: " + getCurrentWatermark().getTimestamp());
                    return element.getTimestamp();
                }
            });

//        withTimestampsAndWatermarks.print();

        env.execute("WindowWordCount");
    }
}
