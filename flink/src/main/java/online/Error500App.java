package online;

import com.google.gson.Gson;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Properties;

/**
 * Created by yidxue on 2019/4/30
 */
public class Error500App {

    public static class Amonaly implements MapFunction<String, String> {

        @Override
        public String map(String in) {
            Gson gson = new Gson();
            HashMap data = gson.fromJson(in, HashMap.class);
            String res = null;
            try {
                res = getDataByRestFul(
                    String.valueOf(data.get("component")),
                    String.valueOf(data.get("servertype")),
                    String.valueOf(data.get("errortype")),
                    String.valueOf(data.get("cluster")),
                    "1",
                    String.valueOf(data.get("date"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }
    }


    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        env.enableCheckpointing(5000);

        // configure Kafka consumer
        Properties props = new Properties();
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("bootstrap.servers", "10.29.42.141:9092,10.29.42.142:9092,10.29.42.143:9092");
        props.setProperty("group.id", "group1");
        String topic = "error500.test";
        FlinkKafkaConsumer010<String> myConsumer = new FlinkKafkaConsumer010<>(topic, new SimpleStringSchema(), props);

        // 添加 kafka 数据源
        DataStream<String> stream = env.addSource(myConsumer.setStartFromEarliest());
        stream.map(new Amonaly()).print();
        env.execute("Error500App");
    }

    public static String getDataByRestFul(String component, String servertype, String errortype, String cluster, String pointNum, String date) throws Exception {
        String stubsApiBaseUri = "http://10.194.110.37:7250/error500/predict_data";
        HttpClient client = HttpClients.createDefault();

        URIBuilder builder = new URIBuilder(stubsApiBaseUri);
        builder.addParameter("date", date);
        builder.addParameter("component", component);
        builder.addParameter("servertype", servertype);
        builder.addParameter("errortype", errortype);
        builder.addParameter("cluster", cluster);
        builder.addParameter("point_num", pointNum);

        String listStubsUri = builder.build().toString();
        HttpGet getStubMethod = new HttpGet(listStubsUri);
        HttpResponse getStubResponse = client.execute(getStubMethod);
        int getStubStatusCode = getStubResponse.getStatusLine()
                                    .getStatusCode();
        if (getStubStatusCode < 200 || getStubStatusCode >= 300) {
            return "";
        }
        return EntityUtils.toString(getStubResponse.getEntity());
    }
}
