package kafka;

import com.typesafe.config.Config;
import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.server.ConfigType;
import kafka.utils.ZkUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.security.JaasUtils;
import org.json.JSONObject;
import java.util.*;


/**
 * @author yiding
 */
public class JavaKafka {

    public JavaKafka(){

    }

    public void kafkaProducer(Config config){
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
            HashMap<String,String> map =new HashMap<>();
            map.put("timestamp", Service.getNowDate());
            map.put("name", Service.getRandomStr());
            map.put("flag", Integer.toString(Service.getRandomInt()));
            producer.send(new ProducerRecord<>(config.getString("kafka_producer.topic"), String.valueOf(new Date().getTime()), new JSONObject(map).toString()));
        }
        producer.close();
    }

    public void kafkaConsumer(Config config) {
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

    public void createTopic(){
        ZkUtils zkUtils = ZkUtils.apply("localhost:2181", 30000, 30000, JaasUtils.isZkSecurityEnabled());
        // 创建一个单分区单副本名为t1的topic
        AdminUtils.createTopic(zkUtils, "t1", 1, 1, new Properties(), RackAwareMode.Enforced$.MODULE$);
        zkUtils.close();
    }

    public void deleteTopic(){
        ZkUtils zkUtils = ZkUtils.apply("localhost:2181", 30000, 30000, JaasUtils.isZkSecurityEnabled());
        // 删除topic 't1'
        AdminUtils.deleteTopic(zkUtils, "t1");
        zkUtils.close();
    }

    public void selectTopic(){
        ZkUtils zkUtils = ZkUtils.apply("localhost:2181", 30000, 30000, JaasUtils.isZkSecurityEnabled());
        // 获取topic 'test'的topic属性属性
        Properties props = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), "map2json");
        // 查询topic-level属性
        Iterator it = props.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry entry=(Map.Entry)it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + " = " + value);
        }
        zkUtils.close();
    }

    public void modifyTopic(){
        ZkUtils zkUtils = ZkUtils.apply("localhost:2181", 30000, 30000, JaasUtils.isZkSecurityEnabled());
        Properties props = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), "map2json");
        // 增加topic级别属性
        props.put("min.cleanable.dirty.ratio", "0.3");
        // 删除topic级别属性
        props.remove("max.message.bytes");
        // 修改topic 'test'的属性
        AdminUtils.changeTopicConfig(zkUtils, "map2json", props);
        zkUtils.close();
    }
}
