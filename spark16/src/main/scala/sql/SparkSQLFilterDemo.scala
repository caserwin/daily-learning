package sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

/**
  * Created by yidxue on 2018/1/29
  */
object SparkSQLFilterDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark join Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", ""),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val inputDF = sc.parallelize(dataSeq1).toDF("id", "name", "city")

    inputDF.filter($"city" !== "").show()

  }
}
