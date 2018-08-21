package datastream.timetype.processtime;

import bean.MyEvent;
import datastream.window.windowfunctions.MyAggregateFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by yidxue on 2018/8/18
 *
 * @author yidxue
 */
public class FlinkProcessTimeDemo {

    public static class SelectMess implements KeySelector<MyEvent, String> {
        @Override
        public String getKey(MyEvent w) {
            return w.getMessage();
        }
    }

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        env.setParallelism(1);

        // 设置数据源
        DataStream<MyEvent> source = env.fromElements(
            new MyEvent(1, "a", 1534581000),
            new MyEvent(2, "b", 1534581005),
            new MyEvent(8, "a", 1534581009),
            new MyEvent(4, "b", 1534581015),
            new MyEvent(8, "a", 1534581020)
        );

        source.keyBy(new SelectMess()).timeWindow(Time.hours(10)).aggregate(new MyAggregateFunction()).setParallelism(1).print();

        env.execute("TimeWindowDemo");
    }
}

