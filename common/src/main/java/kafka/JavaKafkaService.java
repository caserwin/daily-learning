package kafka;

import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.server.ConfigType;
import kafka.utils.ZkUtils;
import org.apache.kafka.common.security.JaasUtils;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author yiding
 */
public class JavaKafkaService {

    public void createTopic(String zk, String topicName) {
        ZkUtils zkUtils = ZkUtils.apply(zk, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        // 创建一个单分区单副本名为t1的topic
        AdminUtils.createTopic(zkUtils, topicName, 1, 1, new Properties(), RackAwareMode.Enforced$.MODULE$);
        zkUtils.close();
    }

    /**
     * 还需要在kafka的配置文件：config/server.properties 加入delete.topic.enable=true 才能使得删除生效。
     */
    public void deleteTopic(String zk, String topicName) {
        ZkUtils zkUtils = ZkUtils.apply(zk, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        AdminUtils.deleteTopic(zkUtils, topicName);
        zkUtils.close();
    }

    public void selectTopic(String zk, String topicName) {
        ZkUtils zkUtils = ZkUtils.apply(zk, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        // 获取topic 'test'的topic属性属性
        Properties props = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), topicName);
        // 查询topic-level属性
        for (Object o : props.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + " = " + value);
        }
        zkUtils.close();
    }

    public void modifyTopic(String zk, String topicName) {
        ZkUtils zkUtils = ZkUtils.apply(zk, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        Properties props = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), "map2json");
        // 增加topic级别属性
        props.put("min.cleanable.dirty.ratio", "0.3");
        // 删除topic级别属性
        props.remove("max.message.bytes");
        // 修改topic 'test'的属性
        AdminUtils.changeTopicConfig(zkUtils, topicName, props);
        zkUtils.close();
    }

    public static void main(String[] args) {

        JavaKafkaService javaKafka = new JavaKafkaService();
        javaKafka.createTopic("localhost:2181", "testByJava");
        javaKafka.modifyTopic("localhost:2181", "testByJava");
        javaKafka.selectTopic("localhost:2181", "testByJava");
        javaKafka.deleteTopic("localhost:2181", "testByJava");
    }
}
