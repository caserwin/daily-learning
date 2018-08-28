package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{concat, lit, when}

/**
  * Created by yidxue on 2018/1/29
  */
object SparkSQLAddOrUpdateColumnDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", null),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq1).toDF("id", "name", "city")
    inputDF.show()

    val outDF = inputDF
      .select($"id", $"name", $"city")
      .withColumn("city", when($"city".isNull, "hangzhou").otherwise($"city")) // update column
      .withColumn("time", concat(lit("2018-01-18")))
      // notice: it is a error when lit(null)
      .withColumn("date", concat(lit("2018-01-18"), lit("_"), lit(null))) // add column
      .drop("name")    // delete column

    outDF.show()
    spark.stop()
  }
}
