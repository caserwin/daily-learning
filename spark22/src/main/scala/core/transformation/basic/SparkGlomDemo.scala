package core.transformation.basic

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/5/7
  * 该函数是将RDD中每一个分区中类型为T的元素转换成Array[T]，这样每一个分区就只有一个数组元素。
  */
object SparkGlomDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("Simple Application").config("spark.master", "local[*]").getOrCreate()
    val rdd = spark.sparkContext.makeRDD(1 to 11, 2)

    val arrayRDD = rdd.glom()
    arrayRDD.foreach(array => println(array.mkString(",")))
    spark.stop()
  }
}
