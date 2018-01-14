package kafka.message

import java.util.Properties
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.kafka.clients.producer._

object KafkaNotifier {

  val conf: Config = ConfigFactory.load()

  def sendMsgToKafka(jsonStr: String): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", conf.getString("pcia.monitor.kafka.address"))
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val topic = conf.getString("pcia.monitor.kafka.topic")
    val record = new ProducerRecord(topic, new String, jsonStr)

    producer.send(record)
    producer.close()
  }

  def main(args: Array[String]): Unit = {
    sendMsgToKafka(KafkaTemplate.getJMTAndJMSTemplate("1234", "2345", "5678", 111))
  }
}
