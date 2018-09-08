package kafka

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object KafkaProducer {

  def sendMsgToKafka(jsonStr: String): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val topic = "test"
    val record = new ProducerRecord(topic, new String, jsonStr)

    producer.send(record)
    producer.close()
  }

  def main(args: Array[String]): Unit = {
    sendMsgToKafka(KafkaTemplate.getJMTAndJMSTemplate("test_topic", "2345", "5678", 111))
  }
}
