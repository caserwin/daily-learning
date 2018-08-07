package sql

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions.{concat, lit, when}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/1/29
  */
object SparkSQLAddOrUpdateColumnDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", null),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val inputDF = sc.parallelize(dataSeq1).toDF("id", "name", "city")

    inputDF
      .select($"id", $"name", $"city")
      .withColumn("city", when($"city".isNull, "hangzhou").otherwise($"city")) // update column
      // notice: it is a error when lit(null)
      .withColumn("date", concat(lit("2018-01-18"), lit("_"), lit(null))) // add column
      .show()

    sc.stop()
  }
}
