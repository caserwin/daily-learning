package sql

import org.apache.spark.sql.SparkSession

object SparkSQLTempTableDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )

    val input1 = spark.sparkContext.parallelize(dataSeq1).toDF("id", "name", "city")

    input1.createOrReplaceTempView("cityinfo")
    spark.sql("select * from cityinfo").show()
  }
}
