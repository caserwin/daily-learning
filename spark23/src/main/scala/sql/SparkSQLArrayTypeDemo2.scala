package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions._
import org.apache.spark.sql.Column

/**
  * Created by yidxue on 2018/7/10
  * 类目打散
  */
object SparkSQLArrayTypeDemo2 {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      ("HangZhou", "a1:1,a2:1,a3:1"),
      ("NewYork", "b:1.5"),
      ("NewYork", "c:1.2"),
      ("ShangHai", "d:1"),
      ("NanJing", "d:2")
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq1).toDF("name", "goods_score")

    def limitSize(n: Int, arrCol: Column): Column = array((0 until n).map(arrCol.getItem): _*)

    val df = inputDF
      .withColumn("goods_score_arr2", limitSize(2, split($"goods_score", ",").cast("array<string>")))
      .drop("goods_score")
      .groupBy("name")
      .agg(collect_list($"goods_score_arr2").alias("goods_score_arr2"))
      .withColumn("goods_recall2", mergeArrayUDF($"goods_score_arr2"))

    df.printSchema()

    df.show(truncate = false)
    spark.stop()
  }

  val mergeArrayUDF: UserDefinedFunction = udf(mergeArray)

  def mergeArray: Seq[Seq[String]] => String = {
    xs => {
      val arr = xs.flatten.distinct
      val goods_confidence_map = arr.filter(item => item != null).map(item => {
        val goods_and_confidence = item.split(":")
        goods_and_confidence(0) -> goods_and_confidence(1).toDouble
      })

      // sort by confidence
      goods_confidence_map.toList.sortBy(-_._2).take(30).map(item => item._1 + ":" + item._2).mkString(",")
    }
  }
}
