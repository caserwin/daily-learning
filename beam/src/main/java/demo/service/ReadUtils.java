package demo.service;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.values.PCollection;
//import org.apache.kafka.common.serialization.LongDeserializer;
//import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * Created by yidxue on 2018/8/31
 */
public class ReadUtils {
//    public PCollection<KV<Long, String>> readFromKafka(Pipeline pipeline, String bootstrapserver, String topic, int maxnum) {
//        return pipeline.apply(KafkaIO.<Long, String>read()
//                                  .withBootstrapServers(bootstrapserver)
//                                  .withTopic(topic)
//                                  .withKeyDeserializer(LongDeserializer.class)
//                                  .withValueDeserializer(StringDeserializer.class)
//                                  .withMaxNumRecords(maxnum)
//                                  .withoutMetadata()
//        );
//    }
//
//    public PCollection<KV<Long, String>> readFromKafka(Pipeline pipeline, String bootstrapserver, String topic) {
//        return pipeline.apply(KafkaIO.<Long, String>read()
//                                  .withBootstrapServers(bootstrapserver)
//                                  .withTopic(topic)
//                                  .withKeyDeserializer(LongDeserializer.class)
//                                  .withValueDeserializer(StringDeserializer.class)
//                                  .withoutMetadata()
//        );
//    }
}
