package sql

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/1/30
  */
object SparkSQLExceptDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val inputDF1 = sc.parallelize(dataSeq1).toDF("id", "name", "city")

    val dataSeq2 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "wenzhou")
    )
    val inputDF2 = sc.parallelize(dataSeq2).toDF("id", "name", "city")

    // 求差集，也就是出现在 inputDF1 ，但是没出现在 inputDF2的数据
    inputDF1.except(inputDF2).show()

    // 根据指定字段求差集，这里根据 city 字段求差集。
    val miss = inputDF1.select($"city").distinct().except(inputDF2.select($"city"))
    inputDF1.join(miss, Seq("city"), "inner").show()

    sc.stop()
  }
}
