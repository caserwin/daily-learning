package kafka.demo;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * 自己手动控制kafka的offset
 * http://blog.csdn.net/qq_20641565/article/details/64440425?locationNum=10&fps=1
 * @author yidxue
 */

public class MyConsumer {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        //设置不自动提交，自己手动更新offset
        properties.put("enable.auto.commit", "false");
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("group.id", "t2");
        properties.put("auto.commit.interval.ms", "1000");
        ExecutorService executor = Executors.newFixedThreadPool(5);

        //执行消费
        for (int i = 0; i < 7; i++) {
            executor.execute(new ConsumerThread(new KafkaConsumer<>(properties), "test,test1", "消费者" + (i + 1)));
        }
    }
}
