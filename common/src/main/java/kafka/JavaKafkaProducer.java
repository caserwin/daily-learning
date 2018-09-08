package kafka;

import com.alibaba.fastjson.JSON;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by yidxue on 2018/9/8
 */
public class JavaKafkaProducer {

    public static void main(String[] args) {
        Config config = ConfigFactory.load();

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", config.getString("kafka_producer.bootstrap_servers"));
        props.setProperty("acks", config.getString("kafka_producer.acks"));
        props.setProperty("retries", config.getString("kafka_producer.retries"));
        props.setProperty("batch.size", config.getString("kafka_producer.batch_size"));
        props.setProperty("linger.ms", config.getString("kafka_producer.linger_ms"));
        props.setProperty("buffer.memory", config.getString("kafka_producer.buffer_memory"));

        props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        System.out.println(props.toString());
        System.out.println(config.getString("kafka_producer.topic"));

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("timestamp", Util.getNowDate());
            map.put("name", Util.getRandomStr());
            map.put("flag", Integer.toString(Util.getRandomInt()));
            producer.send(new ProducerRecord<>(config.getString("kafka_producer.topic"), String.valueOf(System.currentTimeMillis()), JSON.toJSONString(map)));
        }
        producer.close();
    }
}
