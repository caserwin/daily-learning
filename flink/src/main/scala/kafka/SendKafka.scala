package kafka

import java.util.Properties

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.flink.streaming.util.serialization.SimpleStringSchema


object SendKafka {
  @throws[Exception]
  def main(args: Array[String]): Unit = {
    println("scala activeUser")
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
    env.setParallelism(1) //
    env.enableCheckpointing(5000) // 每5秒做一次checkpoint　　
    env.getConfig.setAutoWatermarkInterval(1000)

    // configure Kafka consumer
    val props = new Properties
    props.setProperty("bootstrap.servers", "rpbt1hsn009.webex.com:9092"); // Broker default host:port
    props.setProperty("group.id", "myGroup-2") // Consumer group ID

    // create a Kafka consumer
    val myConsumer = new FlinkKafkaConsumer010[String]("aggr_splunk", new SimpleStringSchema, props)
    myConsumer.setStartFromEarliest()
    val stream = env.addSource(myConsumer)

    // 打印流
    stream.print()
    // 输出到一个文本文件
//    stream.writeAsText("./result.txt")
    // 真正执行语句
    env.execute("Flink-Kafka demo")
  }
}



