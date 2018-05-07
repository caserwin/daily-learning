package util.create

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/5/7
  */
object SparkReadDemo1 {
  def main(args: Array[String]): Unit = {
    val logFile = "data/README.md" // Should be some file on your system
    val spark = SparkSession.builder.appName("Simple Application").config("spark.master", "local[*]").getOrCreate()
    val logData = spark.read.textFile(logFile).cache()
    logData.filter(line => line.contains("which")).foreach(println(_))

    spark.stop()
  }
}
