package steaming

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/3/26
  */
object SparkSteamingReadFileDemo {
  // NOTICE: need be modify
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("JMT").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc, Seconds(1))

    val dstream = ssc.textFileStream("data/data")

    dstream.foreachRDD(rdd => {
      //now apply a transformation or anything with the each rdd
//      spark.read.json(rdd) // to change it to dataframe
      rdd.foreach(println(_))
    })

    ssc.start()             // Start the computation
    ssc.awaitTermination()   // Wait for the computation to terminate
  }
}
