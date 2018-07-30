package core.transformation.basic

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/5/7
  */
object SparkUnionDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("Simple Application").config("spark.master", "local[*]").getOrCreate()
    val rdd1 = spark.sparkContext.makeRDD(1 to 5, 1)
    val rdd2 = spark.sparkContext.makeRDD(6 to 10, 1)
    rdd1.union(rdd2).foreach(println(_))
    spark.stop()
  }
}
