package sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._

/**
  * Created by yidxue on 2018/2/27
  */
object SparkSQLSortDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val df = sqlContext.read.json("data/cityinfo.json")

    // 工具 Cindex 字段排序： desc降序， asc升序
    df.orderBy(df("Cindex").desc).show(false)

    df.orderBy(col("Cindex").asc).show(false)
  }
}
