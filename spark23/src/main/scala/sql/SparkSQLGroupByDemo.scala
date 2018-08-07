package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{count, lit, sum}

/**
  * Created by yidxue on 2018/1/29
  */
object SparkSQLGroupByDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq = Seq(
      ("1", "lisi", "12"),
      ("1", "lisi", "3"),
      ("1", "wangwu", "3"),
      ("2", "wangwu", "4")
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq).toDF("id", "name", "num")

    inputDF.groupBy("id").agg(count(lit(1)).alias("COUNT")).show()
    inputDF.groupBy("id").agg(sum($"num".cast("integer")).alias("COUNT")).show()

    spark.stop()
  }
}
