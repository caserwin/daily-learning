package sql

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/1/31
  */
object SparkSQLExistOrInDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq).toDF("id", "name", "city")

    inputDF.printSchema()

    inputDF.filter($"id".isin(List(1, 2): _*)).show()
    inputDF.filter($"id".isin(Seq(1, 2): _*)).show()

  }
}
