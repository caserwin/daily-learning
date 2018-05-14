package datastream.datasource.kafka;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.connectors.kafka.internals.KafkaTopicPartition;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * @author yidxue
 */
public class KafkaConsumer {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
//        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.getConfig().setAutoWatermarkInterval(1000);
        env.setParallelism(1);
        // 每5秒做一次checkpoint
        env.enableCheckpointing(5000);

        // configure Kafka consumer
        Properties props = new Properties();
        // Broker default host:port
        props.setProperty("bootstrap.servers", "localhost:9092");
        // Consumer group ID
        props.setProperty("group.id", "myGroup-2");
        String topic = "test_persist_2";

        FlinkKafkaConsumer010<String> myConsumer = new FlinkKafkaConsumer010<>(topic, new SimpleStringSchema(), props);

        // set specific offset value
        // myConsumer.setStartFromEarliest();
        Map<KafkaTopicPartition, Long> specificStartOffsets = new HashMap<>();
        specificStartOffsets.put(new KafkaTopicPartition(topic, 0), 10L);
        myConsumer.setStartFromSpecificOffsets(specificStartOffsets);

        DataStream<String> stream = env.addSource(myConsumer);

        // 打印流
        stream.print();

        // 输出到一个文本文件
//        stream.writeAsText("~/result.txt");

        // 真正执行语句
        env.execute("Flink-Kafka demo");
    }
}


