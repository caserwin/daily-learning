package datastream.transformations;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import util.source.WordCountDataSource;

/**
 * Flink 侧流输出
 * 参考：https://blog.csdn.net/rlnLo2pNEfx9c/article/details/86285634
 */
public class FlinkSideOutputDemo {
    private static final OutputTag<String> REJECTED_WORDS_TAG = new OutputTag<String>("rejected") {
    };

    public static void main(String[] args) throws Exception {
        // set up the execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);
        // 获取输入数据
        DataStream<String> text = env.fromElements(WordCountDataSource.WORDS);

        SingleOutputStreamOperator<Tuple2<String, Integer>> tokenized = text.keyBy(new KeySelector<String, Integer>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Integer getKey(String value) throws Exception {
                return 0;
            }
        }).process(new Tokenizer());

        // 获取侧输出
        DataStream<String> rejectedWords = tokenized.getSideOutput(REJECTED_WORDS_TAG).map(new MapFunction<String, String>() {
            private static final long serialVersionUID = 1L;
            @Override
            public String map(String value) throws Exception {
                return "rejected:" + value;
            }
        });

        DataStream<Tuple2<String, Integer>> counts = tokenized.keyBy(0).window(TumblingEventTimeWindows.of(Time.seconds(5))).sum(1);
        // 侧输出结果输出
        rejectedWords.print();
        // wordcount结果输出
        counts.print();

        // executeprogram
        env.execute("StreamingWordCountSideOutput");
    }

    public static final class Tokenizer extends ProcessFunction<String, Tuple2<String, Integer>> {
        private static final long serialVersionUID = 1L;

        @Override
        public void processElement(String value, Context ctx, Collector<Tuple2<String, Integer>> out) throws Exception {
            String[] tokens = value.toLowerCase().split("\\W+");

            for (String token : tokens) {
                if (token.length() > 5) {
                    ctx.output(REJECTED_WORDS_TAG, token);
                } else if (token.length() > 0) {
                    out.collect(new Tuple2<>(token, 1));
                }
            }
        }
    }
}
