package sql

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions.sum
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/9/25
  */
object SparkSQLPivotDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

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
    val inputDF = sc.parallelize(dataSeq).toDF("id", "name", "num")
    inputDF.groupBy("id").pivot("name").agg(sum($"num").alias("sum")).show()

    sc.stop()
  }
}