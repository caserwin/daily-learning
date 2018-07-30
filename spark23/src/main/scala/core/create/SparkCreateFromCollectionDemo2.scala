package core.create

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/4/4
  */
object SparkCreateFromCollectionDemo2 {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SparkCreateRDDDemo").config("spark.master", "local[*]").getOrCreate()
    val data = List("hi", "spark cluster", "hi", "spark")
    val rdd = spark.sparkContext.parallelize(data)
    rdd.foreach(println(_))
  }
}
