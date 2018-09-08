package kafka;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * Created by yidxue on 2018/9/8
 */
public class JavaKafkaConsumer {

    public static void main(String[] args) {
        Config config = ConfigFactory.load();

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", config.getString("kafka_comsumer.bootstrap_servers"));
        props.setProperty("auto.offset.reset", config.getString("kafka_comsumer.auto_offset_reset"));
        props.setProperty("group.id", config.getString("kafka_comsumer.group_id"));
        props.setProperty("enable.auto.commit", config.getString("kafka_comsumer.enable_auto_commit"));
        props.setProperty("auto.commit.interval.ms", "1000");

        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(config.getString("kafka_comsumer.topic")));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("partition = %d, offset = %d, key = %s, value = %s%n", record.partition(), record.offset(), record.key(), record.value());
            }
        }
    }
}
