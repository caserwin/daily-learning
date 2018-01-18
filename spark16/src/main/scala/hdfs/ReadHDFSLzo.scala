package hdfs

import org.apache.spark.{SparkConf, SparkContext}

object ReadHDFSLzo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setAppName("Simple Application")
    val sc = new SparkContext(sparkConf)
    val filePath = args(0)

    val lzoFile = sc.newAPIHadoopFile(filePath,
      classOf[com.hadoop.mapreduce.LzoTextInputFormat],
      classOf[org.apache.hadoop.io.LongWritable],
      classOf[org.apache.hadoop.io.Text]).map(_._2.toString)

    val lzoWordCounts = lzoFile.flatMap(x => x.split("-")).map((_, 1)).reduceByKey(_ + _)

    println(lzoWordCounts.first())
    println(lzoFile.partitions.length)

  }
}
