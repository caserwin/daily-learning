package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

/**
  * Created by yidxue on 2018/4/12
  *
  * 注意sort的话，是比较消耗 driver menory的，必须调大点。
  * 还有一点，sort后 driver 向executor 发送的数据会变大，有可能会超过spark默认的传输限制
  * 描述参考：https://www.jianshu.com/p/abac341fdacf
  * 解决参考：https://docs.databricks.com/spark/latest/faq/spark-serialized-task-is-too-large.html
  */
object SparkSQLSortDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application")
      .config("spark.rpc.message.maxSize", "2014")
      .config("spark.master", "local[*]")
      .getOrCreate()

    val df = spark.read.json("data/cityinfo.json")

    // 工具 Cindex 字段排序： desc降序， asc升序
    df.orderBy(df("Cindex").desc).show(false)

    df.orderBy(col("Cindex").asc).show(false)
    df.orderBy(col("Cindex").asc).limit(3).show(false)

    spark.stop()
  }
}
