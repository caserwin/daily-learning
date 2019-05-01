package kafka;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import tools.DateUtil;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by yidxue on 2018/9/8
 */
public class Error500Demo {


    public static void main(String[] args) throws FileNotFoundException {
        Gson gson = new Gson();
        String filename = "/Users/cisco/workspace/mygit/daily-learning/common/src/main/resources/fail_data";
        JsonReader reader = new JsonReader(new FileReader(filename));
        HashMap data = gson.fromJson(reader, HashMap.class);
        ArrayList<LinkedTreeMap<String, String>> ls = (ArrayList<LinkedTreeMap<String, String>>) data.get("context");

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

        for (LinkedTreeMap<String, String> l : ls) {
            HashMap<String, String> map = new HashMap<>();
            map.put("component", "meeting");
            map.put("servertype", "mcrsvr");
            map.put("cluster", "L");
            map.put("errortype", "mcrsvrmcrmcode");
            map.put("fail", l.get("fail"));
            map.put("date", DateUtil.transFormat(l.get("date"), "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss"));
            producer.send(new ProducerRecord<>(config.getString("kafka_producer.topic"), String.valueOf(System.currentTimeMillis()), JSON.toJSONString(map)));
        }
        producer.close();
    }
}
