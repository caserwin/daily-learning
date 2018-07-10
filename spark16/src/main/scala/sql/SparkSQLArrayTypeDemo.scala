package sql

import org.apache.spark.sql.functions.{array_contains, collect_list, when}
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/7/10
  */
object SparkSQLArrayTypeDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new HiveContext(sc)
    import sqlContext.implicits._

    val dataSeq1 = Seq(
      ("HangZhou", 1.7, "CN", "aaa"),
      ("NewYork", 1.7, "US", "downloadstart"),
      ("Halifax", 3.0, "US", "downloadend"),
      ("ShangHai", 2.2, "CN", "aaa"),
      ("NanJing", 1.0, "CN", "aaa")
    )
    val inputDF = sc.parallelize(dataSeq1).toDF("name", "Cindex", "country", "et")

    inputDF.groupBy("country")
      .agg(collect_list($"et").alias("et"))
      .withColumn("FLAG", when(array_contains($"et", "aaa") || array_contains($"et", "bbb"), "TRUE").otherwise("FALSE"))
      .show(truncate = false)
  }
}
