package util.create

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{explode, udf}

/**
  * Created by yidxue on 2018/1/31
  */
object SparkReadJsonToDFDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("Simple Application").config("spark.master", "local[*]").getOrCreate()

    import spark.implicits._

    val data = spark.sparkContext.parallelize(Seq(
      """{"userId": 1, "someString": "example1",
        "varA": [0, 2, 5], "varB": [1, 2, 9]}""",
      """{"userId": 2, "someString": "example2",
        "varA": [1, 20, 5], "varB": [9, null, 6]}"""
    ))

    val df = spark.sqlContext.read.json(data.toDS())
    df.show()

    val zip = udf((xs: Seq[Long], ys: Seq[Long]) => xs.zip(ys))

    df.withColumn("vars", explode(zip($"varA", $"varB")))
      .select(
        $"userId", $"someString",
        $"vars._1".alias("varA"),
        $"vars._2".alias("varB")
      ).show()


    val a = "sss"
    val b = "yyy"

    println(a.zip(b))
  }
}
