package sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

/**
  * Created by yidxue on 2018/1/31
  */
object SparkSQLExistOrInDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val dataSeq = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val inputDF = sc.parallelize(dataSeq).toDF("id", "name", "city")

    inputDF.printSchema()

    inputDF.filter($"id".isin(List(1, 2): _*)).show()
    inputDF.filter($"id".isin(Seq(1, 2): _*)).show()

  }
}
