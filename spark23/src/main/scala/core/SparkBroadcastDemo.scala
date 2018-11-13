package core

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/11/12
  */
object SparkBroadcastDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()

    val dataSeq = Array("zhangsan  1", "lisi 2", "yidxue 3").toSet
    val broadcast = spark.sparkContext.broadcast(dataSeq)

    val observedSizes = spark.sparkContext.parallelize(1 to 10, 2).map(a => broadcast.value.size + "\t" + a)

    observedSizes.foreach(println)
    println(broadcast.value.getClass)
    spark.stop()
  }
}
