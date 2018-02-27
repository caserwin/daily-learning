package sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

/**
  * Created by yidxue on 2018/2/6
  */
object SparkSQLJoinDemo2 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan"),
      (2, "lisi"),
      (3, "wangwu")
    )
    val input1 = sc.parallelize(dataSeq1).toDF("id", "name")

    val dataSeq2 = Seq(
      (1, "hangzhou"),
      (2, "beijing"),
      (3, "shanghai")
    )
    val input2 = sc.parallelize(dataSeq2).toDF("id", "city")


    val dataSeq3 = Seq(
      ("hangzhou", "a"),
      ("hangzhou", "b"),
      ("shanghai", "c")
    )
    val input3 = sc.parallelize(dataSeq3).toDF("city", "flag")

    input1.join(input2, Seq("id"), "left").join(input3, Seq("city"), "left").show()

  }
}
