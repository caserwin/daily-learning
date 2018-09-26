package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.sum

/**
  * Created by yidxue on 2018/9/25
  */
object SparkSQLPivotDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq = Seq(
      ("1", "lisi", "4"),
      ("1", "lisi", "10"),
      ("1", "lisi", "4"),
      ("1", "lisi", "10"),
      ("2", "lisi", "4"),
      ("2", "lisi", "10"),
      ("2", "wangwu", "3"),
      ("2", "wangwu", "4")
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq).toDF("id", "name", "num")
    inputDF.groupBy("id").pivot("name").agg(sum($"num").alias("sum")).show()

    spark.stop()
  }
}