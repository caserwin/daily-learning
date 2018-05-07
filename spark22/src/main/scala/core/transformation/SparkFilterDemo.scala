package core.transformation

import org.apache.spark.sql.SparkSession

object SparkFilterDemo {

  def main(args: Array[String]) {
    val spark = SparkSession.builder.appName("Simple Application").config("spark.master", "local[*]").getOrCreate()
    val rdd = spark.sparkContext.parallelize(Array("erwin", "caroline","yid","tiq"))
    rdd.filter(name=>name.contains("a")).foreach(println(_))
    spark.stop()
  }
}
