package kafka.message

import java.util.Properties
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.kafka.clients.producer._

object KafkaNotifier {

  val conf: Config = ConfigFactory.load()

  def sendMsgToKafka(jsonStr: String): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val topic = "sj1_sap_pipeLine_data_integrity_pda_monitor"
    val record = new ProducerRecord(topic, new String, jsonStr)

    producer.send(record)
    producer.close()
  }

  def main(args: Array[String]): Unit = {
    sendMsgToKafka(KafkaTemplate.getJMTAndJMSTemplate("test_topic", "2345", "5678", 111))
  }
}
