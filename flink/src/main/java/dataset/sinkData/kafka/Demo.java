package dataset.sinkData.kafka;

import com.alibaba.fastjson.JSON;
import dataset.sinkData.kafka.bean.Json;
import dataset.sinkData.kafka.method2.KafkaOutputFormat;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

import java.util.Random;

/**
 * @author yidxue
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSet<String> value = env.fromElements(jsonBuilder());

        // meyhod1: 发送 json 格式数据
//        List<String> resLs = value.collect();
//        SinkToKafka.kafkaProducer(resLs);

        // method2: sink to Kafka
        value.output(
            KafkaOutputFormat.buildKafkaOutputFormat()
                .setBootstrapServers("localhost:9092")
                .setTopic("Test_Topic_1")
                .setAcks("all")
                .setBatchSize("10")
                .setBufferMemory("10240")
                .setLingerMs("1")
                .setRetries("0")
                .finish()
        );
        env.execute();
    }

    private static String[] jsonBuilder() throws InterruptedException {
        String[] strs = new String[10];
        Random random = new Random();
        Json json;
        for (int i = 0; i < strs.length; i++) {
            long timestamp = System.currentTimeMillis();
            int id = random.nextInt(100);
            String name = String.valueOf("abcdefghig".charAt(random.nextInt(9)));
            json = new Json(timestamp, id, name);

            strs[i] = JSON.toJSONString(json);
            Thread.sleep(1);
        }
        return strs;
    }
}
