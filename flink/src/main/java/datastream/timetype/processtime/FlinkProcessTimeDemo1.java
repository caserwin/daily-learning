package datastream.timetype.processtime;

import bean.MyEvent;
import datastream.window.windowfunctions.function.MyAggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by yidxue on 2018/8/18
 */
public class FlinkProcessTimeDemo1 {

    public static class StrToEventMap implements MapFunction<String, MyEvent> {
        @Override
        public MyEvent map(String in) {
            String[] tmp = in.trim().split("\\W+");

            return new MyEvent(Integer.parseInt(tmp[0]), tmp[1], 123456789);
        }
    }


    public static class SelectMess implements KeySelector<MyEvent, String> {
        @Override
        public String getKey(MyEvent w) {
            return w.getMessage();
        }
    }

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置数据源
        DataStream<Double> source = env.socketTextStream("localhost", 9998)
                                        .map(new StrToEventMap())
                                        .keyBy(new SelectMess())
                                        .timeWindow(Time.seconds(10))
                                        .aggregate(new MyAggregateFunction());

        source.print();
        env.execute("TimeWindowDemo");
    }
}
