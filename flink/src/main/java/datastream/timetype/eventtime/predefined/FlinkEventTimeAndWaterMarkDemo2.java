package datastream.timetype.eventtime.predefined;

import bean.MyEvent;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by yidxue on 2018/8/19
 *
 * @author yidxue
 */
public class FlinkEventTimeAndWaterMarkDemo2 {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);

        DataStream<MyEvent> stream = env.fromElements(
            new MyEvent(1, "a", 1534581000),
            new MyEvent(2, "b", 1534581005),
            new MyEvent(3, "c", 1534581015),
            new MyEvent(4, "d", 1534580000)
        );

        DataStream<MyEvent> withTimestampsAndWatermarks =
            stream.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<MyEvent>(Time.seconds(10)) {
                @Override
                public long extractTimestamp(MyEvent element) {
                    return element.getTimestamp();
                }
            });

        withTimestampsAndWatermarks.print();

        env.execute("WindowWordCount");
    }
}
