package sql

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/1/30
  */
object SparkSQLUnionDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val inputDF1 = spark.sparkContext.parallelize(dataSeq1).toDF("id", "name", "city")

    val dataSeq2 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "wenzhou")
    )
    val inputDF2 = spark.sparkContext.parallelize(dataSeq2).toDF("id", "name", "city")

    inputDF1.union(inputDF2).show()

    spark.stop()
  }
}
