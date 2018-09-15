package datastream.datasource.kafka;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.connectors.kafka.internals.KafkaTopicPartition;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author yidxue
 * https://ci.apache.org/projects/flink/flink-docs-release-1.6/dev/connectors/kafka.html
 */
public class FlinkKafkaConsumer {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        env.enableCheckpointing(5000);

        // configure Kafka consumer
        Properties props = new Properties();
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "group1");
        String topic = "test";
        FlinkKafkaConsumer010<String> myConsumer = new FlinkKafkaConsumer010<>(topic, new SimpleStringSchema(), props);

        // 设置offset 从最开始读取。
        //        myConsumer.setStartFromEarliest();

        // 指定 offset 位置
        Map<KafkaTopicPartition, Long> specificStartOffsets = new HashMap<>();
        specificStartOffsets.put(new KafkaTopicPartition(topic, 0), 2L);

        // 添加 kafka 数据源
        DataStream<String> stream = env.addSource(myConsumer.setStartFromSpecificOffsets(specificStartOffsets));
        stream.print();

        // 执行
        env.execute("kafkaSourceDemo");
    }
}


