package dataset.kafka.method1;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;
import java.util.Properties;

/**
 * @author yidxue
 */
public class SinkToKafka {
    private static Config config = ConfigFactory.load();

    public static void kafkaProducer(List<String> resLS) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("acks", "all");
        props.setProperty("retries", "0");
        props.setProperty("batch.size", "10");
        props.setProperty("linger.ms", "1");
        props.setProperty("buffer.memory", "10240");
        props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        for (String context : resLS) {
            producer.send(new ProducerRecord<>(config.getString("kafka_producer.topic"), String.valueOf(System.currentTimeMillis()), context));
        }

        producer.close();
    }
}
