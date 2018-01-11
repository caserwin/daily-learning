package kafka

import java.util
import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.json.JSONObject

/**
  * User: Erwin
  * Date: 17/11/30 上午11:28
  * Description:
  *  经过测试要发的目标topic必须事先存在，否则数据丢失
  *
  */
object SendKafka {

  def main(args: Array[String]): Unit = {
    sendMsgToKafka()
  }

  def sendMsgToKafka(): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "rpbt1hsn008.webex.com:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val topic = "bt1_sap_pipeLine_data_integrity_pda_monitor_tmp"
    val map: util.HashMap[String, String] = new util.HashMap[String, String]
    map.put("timestamp", "11111")
    map.put("name", "22222")
    map.put("flag", "True")

    producer.send(new ProducerRecord(topic, new String, new JSONObject(map).toString))
    producer.close()
  }
}
