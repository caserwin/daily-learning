package sql.myudf

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

/**
  * Created by yidxue on 2018/10/30
  */
object SparkSQLUDFDemo4 {

  def myFunc: String => Array[String] = { s => Array(s.toLowerCase, s.toUpperCase) }

  val myUDF: UserDefinedFunction = udf(myFunc)

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val inputDF1 = spark.sparkContext.parallelize(dataSeq1).toDF("id", "name", "city")
    val newDF = inputDF1.withColumn("newCol", myUDF(inputDF1("city"))).select($"id", $"name", $"city", $"newCol" (0).as("Slope"), $"newCol" (1).as("Offset"))

    newDF.show()
    spark.stop()
  }
}
