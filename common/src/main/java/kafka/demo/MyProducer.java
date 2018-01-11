package kafka.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * http://blog.csdn.net/qq_20641565/article/details/64440425?locationNum=10&fps=1
 * @author yidxue
 */
public class MyProducer {

    private static KafkaProducer<String, String> pro;
    static {
        //配置
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");

        //序列化类型
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //创建生产者
        pro = new KafkaProducer<>(properties);
    }

    public static void main(String[] args) throws Exception {
        produce("mytopic");
    }

    public static void produce(String topic) throws Exception {
        //模拟message
        for (int i = 0; i < 10000; i++) {
            //封装message
            ProducerRecord<String, String> pr = new ProducerRecord<>(topic, i + "");
            //发送消息
            pro.send(pr);
            Thread.sleep(1000);
        }
    }
}
