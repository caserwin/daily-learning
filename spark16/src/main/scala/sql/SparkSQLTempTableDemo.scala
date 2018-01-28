package sql

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object SparkSQLTempTableDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )

    val input1 = sc.parallelize(dataSeq1).toDF("id", "name", "city")

    input1.registerTempTable("cityinfo")
    sqlContext.sql("select * from cityinfo").show()
  }
}
