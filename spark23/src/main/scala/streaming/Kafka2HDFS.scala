package streaming

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SaveMode, SparkSession}
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.{CanCommitOffsets, HasOffsetRanges, KafkaUtils}
import org.apache.spark.streaming.{Seconds, StreamingContext}


object Kafka2HDFS {

  def run(args: Array[String], parse: String => Row): Unit = {
    if (args.length != 4) {
      System.exit(1)
    }
    val path = args(0)
    val topicName = args(1)
    val groupId = args(2)
    val servers = args(3)

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> servers,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> groupId,
      "auto.offset.reset" -> "earliest",
      "enable.auto.commit" -> (false: java.lang.Boolean),
      "max.partition.fetch.bytes" -> "4194304",
      "replica.fetch.max.bytes" -> "4194304",
      "session.timeout.ms" -> "120000",
      "request.timeout.ms" -> "150000",
      "compression.codec" -> "snappy"
    )

    val sparkConf: SparkConf = new SparkConf().setAppName("Kafka2HDFS" + "_" + topicName)
    val ssc = new StreamingContext(sparkConf, Seconds(900))
    val topics = Array(topicName)
    val stream = KafkaUtils.createDirectStream[String, String](ssc, PreferConsistent, Subscribe[String, String](topics, kafkaParams))
    writeToFile(path, stream, parse)
    ssc.start()
    ssc.awaitTermination()
  }

  def writeToFile(path: String, stream: InputDStream[ConsumerRecord[String, String]], parse: String => Row): Unit = {
    stream.foreachRDD(rdd => {
      try {
        val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
        val spark = SparkSession.builder.config(rdd.sparkContext.getConf).enableHiveSupport().getOrCreate()
        import spark.implicits._
        val schema = StructType(Array(StructField("day", StringType, nullable = false), StructField("log", StringType, nullable = true)))
        val logDF = spark.createDataFrame(rdd.map(r => parse(r.value())), schema)
        logDF.repartition($"day").write.partitionBy("day").mode(SaveMode.Append).text(path)
        stream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
      } catch {
        case e: Throwable => e.printStackTrace()
      }
    })
  }
}

