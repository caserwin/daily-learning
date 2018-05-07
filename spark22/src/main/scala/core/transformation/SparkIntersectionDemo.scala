package core.transformation

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/5/7
  * 该函数返回两个RDD的交集，并且去重。
  */
object SparkIntersectionDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("Simple Application").config("spark.master", "local[*]").getOrCreate()

    val rdd1 = spark.sparkContext.makeRDD(1 to 2,1)
    val rdd2 = spark.sparkContext.makeRDD(2 to 3,1)

    rdd1.foreach(println(_))
    rdd2.foreach(println(_))

    rdd1.intersection(rdd2).foreach(println(_))
  }
}
