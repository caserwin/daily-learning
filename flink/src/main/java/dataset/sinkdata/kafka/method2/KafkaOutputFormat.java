package dataset.sinkdata.kafka.method2;

import org.apache.flink.api.common.io.RichOutputFormat;
import org.apache.flink.configuration.Configuration;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Properties;


/**
 * @author yidxue
 */
public class KafkaOutputFormat extends RichOutputFormat<String> {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaOutputFormat.class);

    private String servers;
    private String topic;
    private String acks;
    private String retries;
    private String batchSize;
    private String bufferMemory;
    private String lingerMS;

    private Producer<String, String> producer;


    @Override
    public void configure(Configuration parameters) {

    }

    @Override
    public void open(int taskNumber, int numTasks) throws IOException {
        Properties props = new Properties();

        props.setProperty("bootstrap.servers", this.servers);
        if (this.acks != null) {
            props.setProperty("acks", this.acks);
        }

        if (this.retries != null) {
            props.setProperty("retries", this.retries);
        }

        if (this.batchSize != null) {
            props.setProperty("batch.size", this.batchSize);
        }

        if (this.lingerMS != null) {
            props.setProperty("linger.ms", this.lingerMS);
        }

        if (this.bufferMemory != null) {
            props.setProperty("buffer.memory", this.bufferMemory);
        }

        props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(props);
    }

    @Override
    public void writeRecord(String record) throws IOException {
        producer.send(new ProducerRecord<>(this.topic, String.valueOf(System.currentTimeMillis()), record));
    }

    @Override
    public void close() throws IOException {
        producer.close();
    }


    public static KafkaOutputFormatBuilder buildKafkaOutputFormat() {
        return new KafkaOutputFormatBuilder();
    }


    public static class KafkaOutputFormatBuilder {
        private final KafkaOutputFormat format;

        public KafkaOutputFormatBuilder() {
            this.format = new KafkaOutputFormat();
        }

        public KafkaOutputFormatBuilder setBootstrapServers(String servers) {
            format.servers = servers;
            return this;
        }

        public KafkaOutputFormatBuilder setTopic(String topic) {
            format.topic = topic;
            return this;
        }

        public KafkaOutputFormatBuilder setAcks(String acks) {
            format.acks = acks;
            return this;
        }

        public KafkaOutputFormatBuilder setRetries(String retries) {
            format.retries = retries;
            return this;
        }

        public KafkaOutputFormatBuilder setBatchSize(String batchSize) {
            format.batchSize = batchSize;
            return this;
        }

        public KafkaOutputFormatBuilder setBufferMemory(String bufferMemory) {
            format.bufferMemory = bufferMemory;
            return this;
        }

        public KafkaOutputFormatBuilder setLingerMs(String lingerMS) {
            format.lingerMS = lingerMS;
            return this;
        }


        public KafkaOutputFormat finish() {
            if (format.servers == null) {
                LOG.info("servers was not supplied separately.");
            }
            if (format.topic == null) {
                LOG.info("topic was not supplied separately.");
            }
            return format;
        }
    }
}
