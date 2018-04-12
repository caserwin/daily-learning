package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

/**
  * Created by yidxue on 2018/4/12
  */
object SparkSQLSortDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()

    val df = spark.read.json("data/cityinfo.json")

    // 工具 Cindex 字段排序： desc降序， asc升序
    df.orderBy(df("Cindex").desc).show(false)

    df.orderBy(col("Cindex").asc).show(false)
  }
}
