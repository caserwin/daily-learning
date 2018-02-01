package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{count, lit}

/**
  * Created by yidxue on 2018/1/29
  */
object SparkSQLGroupByDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq = Seq(
      ("1", "lisi"),
      ("1", "lisi"),
      ("1", "wangwu"),
      ("2", "wangwu")
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq).toDF("id", "name")

    inputDF.groupBy("id").agg(count(lit(1)).alias("COUNT")).show()
  }
}
