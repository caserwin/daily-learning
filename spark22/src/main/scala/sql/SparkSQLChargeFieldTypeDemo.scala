package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.IntegerType

/**
  * Created by yidxue on 2018/1/31
  */
object SparkSQLChargeFieldTypeDemo {

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

    inputDF.printSchema()
    val inputDF1 = inputDF.withColumn("id", $"id".cast("string"))
    inputDF1.printSchema()
    val inputDF2 = inputDF1.withColumn("id", $"id".cast(IntegerType))
    inputDF2.printSchema()
  }
}
