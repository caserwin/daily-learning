package sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions.{count, lit}

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
      ("1", "lisi"),
      ("1", "lisi"),
      ("1", "wangwu"),
      ("2", "wangwu")
    )
    val inputDF = sc.parallelize(dataSeq).toDF("id", "name")

    // 聚合操作
    inputDF.groupBy("id").agg(count(lit(1)).alias("COUNT")).show()
    inputDF.groupBy("id").count().show()
  }
}
