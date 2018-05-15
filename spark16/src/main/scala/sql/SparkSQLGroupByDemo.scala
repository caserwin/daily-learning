package sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._

/**
  * Created by yidxue on 2018/1/29
  */
object SparkSQLGroupByDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val dataSeq = Seq(
      ("1", "lisi", "1"),
      ("1", "lisi", "2"),
      ("1", "wangwu", "3"),
      ("2", "wangwu", "4")
    )
    val inputDF = sc.parallelize(dataSeq).toDF("id", "name", "num")

    // Common aggregation operation
    println(inputDF.groupBy("id").agg(max("num").alias("max")).count())
    // groupBy() 反回的是 GroupedData ，所以groupBy() 后调用 count() 会执行分组统计
    inputDF.groupBy("id").count().show()
    inputDF
      .groupBy("id", "name")
      .agg(
        count(lit(1)).alias("count"),
        max("num").cast("string").alias("max_num"),
        sum("num").cast("string").alias("sum_num"),
        min("num").cast("string").alias("min_num"),
        first($"num").alias("f_num"),
        last($"num").alias("l_num")
      )
      .show()
  }
}
