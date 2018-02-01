package sql

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/1/30
  *
  * http://blog.csdn.net/dabokele/article/details/52802150
  */
object SparkSQLDistinctDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      (1, "lisi"),
      (1, "lisi"),
      (1, "wangwu"),
      (2, "wangwu")
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq1).toDF("id", "name")

    // method1: distinct
    inputDF.distinct().show()
    // method2: dropDuplicates
    inputDF.dropDuplicates(Seq("id")).show()
  }
}
