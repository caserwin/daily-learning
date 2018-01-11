package kafka;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author yidxue
 */
public class JavaKafkaMain {
    public static void main(String[] args) {
        Config config = ConfigFactory.load();

        JavaKafka javaKafkaService = new JavaKafka();
        javaKafkaService.kafkaConsumer(config);
    }
}
