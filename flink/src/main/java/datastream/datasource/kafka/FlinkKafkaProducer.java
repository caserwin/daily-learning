package datastream.datasource.kafka;

import util.bean.WCBean;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer010;

/**
 * Created by yidxue on 2018/9/8
 * https://ci.apache.org/projects/flink/flink-docs-release-1.6/dev/connectors/kafka.html
 */
public class FlinkKafkaProducer {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<WCBean> input = env.fromElements(
            new WCBean("Hello", 1),
            new WCBean("Ciao", 1),
            new WCBean("Hello", 1));

        FlinkKafkaProducer010<String> myProducer = new FlinkKafkaProducer010<>("localhost:9092", "my-topic", new SimpleStringSchema());
        myProducer.setWriteTimestampToKafka(true);

        input.map(WCBean::toString).addSink(myProducer);
        env.execute("kafkaSinkDemo");
    }
}
