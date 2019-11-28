package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions._

/**
  * Created by yidxue on 2018/7/10
  */
object SparkSQLArrayTypeDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      ("HangZhou", 1.7, "CN", "aaa1"),
      ("NewYork", 1.7, "US", "downloadstart"),
      ("Halifax", 3.0, "US", "downloadend"),
      ("ShangHai", 2.2, "CN", "aaa2"),
      ("NanJing", 1.0, "CN", "aaa3")
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq1).toDF("name", "Cindex", "country", "et")

    val df = inputDF.groupBy("country")
      .agg(
        collect_list($"et").alias("et"),
        concat_ws(",", collect_list($"Cindex")).alias("Cindex")
      )
      .withColumn("FLAG", when(array_contains($"et", "aaa") || array_contains($"et", "bbb"), "TRUE").otherwise("FALSE"))
      .withColumn("ETNEW", myUDF($"et", $"FLAG"))

    df.show(truncate = false)
    spark.stop()
  }

  val myUDF: UserDefinedFunction = udf(myFunc)

  def myFunc: (Seq[String], String) => String = { (s, i) => s.mkString(",") + "\t" + i }

}
