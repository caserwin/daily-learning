package core.create

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions.{explode, udf}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/1/31
  */
object SparkReadJsonToDFDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("simple demo").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val data = sc.parallelize(Seq(
      """{"userId": 1, "someString": "example1",
        "varA": [0, 2, 5], "varB": [1, 2, 9]}""",
      """{"userId": 2, "someString": "example2",
        "varA": [1, 20, 5], "varB": [9, null, 6]}"""
    ))

    val df = sqlContext.read.json(data)
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
