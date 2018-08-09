package sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._

/**
  * Created by yidxue on 2018/2/27
  *
  * 注意sort的话，是比较消耗 driver menory的，必须调大点。
  * 还有一点，sort后 driver 向executor 发送的数据会变大，有可能会超过spark默认的传输限制
  * 描述参考：https://www.jianshu.com/p/abac341fdacf
  * 解决参考：https://docs.databricks.com/spark/latest/faq/spark-serialized-task-is-too-large.html
  */
object SparkSQLSortDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[*]")
    conf.set("spark.akka.frameSize", "1024")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val df = sqlContext.read.json("data/cityinfo.json")

    // 工具 Cindex 字段排序： desc降序， asc升序
    df.orderBy(df("Cindex").desc).show(false)

    df.orderBy(col("Cindex").asc).show(false)

    sc.stop()
  }
}
