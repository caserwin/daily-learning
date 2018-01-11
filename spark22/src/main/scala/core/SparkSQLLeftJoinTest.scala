package core

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object SparkSQLLeftJoinTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Scala UDAF Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val input1 = sc.parallelize(dataSeq1).toDF("id", "name", "city")


    val dataSeq2 = Seq(
      (1, "US"),
      (2, "US1"),
      (2, "CN")
    )
    val input2 = sc.parallelize(dataSeq2).toDF("id", "country")


    val res = input1.join(input2,Seq("id"), "left_outer")
    res.show()

//    input1.filter($"name"==="zhangsan").show()

  }
}
