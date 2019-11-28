package datastream.transformations;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction;
import org.apache.flink.util.Collector;

/**
 * @author yidxue
 * https://stackoverflow.com/questions/43520577/how-to-filter-apache-flink-stream-on-the-basis-of-other
 */
public class FlinkConnectDemo2 {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Event> control = env.fromElements(
                new Event(17),
                new Event(42))
                .keyBy("key");

        DataStream<Event> data = env.fromElements(
                new Event(2),
                new Event(42),
                new Event(6),
                new Event(17),
                new Event(8),
                new Event(42))
                .keyBy("key");

        DataStream<Event> result = control.connect(data).flatMap(new MyConnectedStreams());
        result.print();

        env.execute();
    }

    static final class MyConnectedStreams extends RichCoFlatMapFunction<Event, Event, Event> {

        private ValueState<Boolean> seen = null;

        @Override
        public void open(Configuration config) {
            ValueStateDescriptor<Boolean> descriptor = new ValueStateDescriptor<>(
                    // state name
                    "have-seen-key",
                    // type information of state
                    TypeInformation.of(new TypeHint<Boolean>() {
                    }));
            seen = getRuntimeContext().getState(descriptor);
        }

        @Override
        public void flatMap1(Event control, Collector<Event> out) throws Exception {
            seen.update(Boolean.TRUE);
        }

        @Override
        public void flatMap2(Event data, Collector<Event> out) throws Exception {
            if (seen.value().equals(Boolean.TRUE)) {
                out.collect(data);
            }
        }
    }


    public static final class Event {
        public Event() {
        }

        public Event(int key) {
            this.key = key;
        }

        public int key;

        @Override
        public String toString() {
            return String.valueOf(key);
        }
    }
}
