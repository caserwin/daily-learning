package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object SparkSQLWhenCaseDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou", "0"),
      (2, "lisi", "beijing", "1"),
      (3, "wangwu", "shanghai", "1")
    )

    val df = spark.sparkContext.parallelize(dataSeq1).toDF("id", "name", "city", "sex")
    df
      .withColumn("sex", when($"sex" === "1", "男").otherwise("女"))
      .withColumn("ddd", lit(null))
      .show()

    spark.stop()
  }
}
