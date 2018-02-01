package sql

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/1/30
  *
  * http://blog.csdn.net/dabokele/article/details/52802150
  */
object SparkSQLDistinctDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val dataSeq1 = Seq(
      (1, "lisi"),
      (1, "lisi"),
      (1, "wangwu"),
      (2, "wangwu")
    )
    val inputDF = sc.parallelize(dataSeq1).toDF("id", "name")

    // method1: distinct
    inputDF.distinct().show()
    // method2: dropDuplicates
    inputDF.dropDuplicates(Seq("id")).show()
  }
}
