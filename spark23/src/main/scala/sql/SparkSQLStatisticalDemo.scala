package sql

import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import Numeric.Implicits._

/**
  * Created by yidxue on 2018/12/27
  */
object SparkSQLStatisticalDemo {

  def mean[T: Numeric](xs: Iterable[T]): Double = xs.sum.toDouble / xs.size

  def stdDev[T: Numeric](xs: Iterable[T]): Double = {
    val avg = mean(xs)
    math.sqrt(xs.map(_.toDouble).map(a => math.pow(a - avg, 2)).sum / xs.size)
  }

  val methodMatch: UserDefinedFunction = udf((seq: Seq[Int], method: String) => {
    method match {
      case "0" => seq.min
      case "25" => seq.sorted.toSeq((0.25 * seq.length).toInt)
      case "50" => seq.sorted.toSeq((0.50 * seq.length).toInt)
      case "75" => seq.sorted.toSeq((0.75 * seq.length).toInt)
      case "100" => seq.max
      case "avg" => mean(seq)
      case "stddev" => stdDev(seq)
      case _ => mean(seq)
    }
  })

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq = Seq(
      ("1", Seq(1, 2, 3, 4)),
      ("1", Seq(4, 5, 6)),
      ("1", Seq(6, 7, 8)),
      ("2", Seq(1, 3, 4))
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq).toDF("id", "array")
    inputDF.printSchema()

    inputDF
      .withColumn("0Array", methodMatch($"array", lit("0")))
      .withColumn("25Array", methodMatch($"array", lit("25")))
      .withColumn("50Array", methodMatch($"array", lit("50")))
      .withColumn("75Array", methodMatch($"array", lit("75")))
      .withColumn("100Array", methodMatch($"array", lit("100")))
      .withColumn("avgArray", methodMatch($"array", lit("avg")))
      .withColumn("stddevArray", methodMatch($"array", lit("stddev")))
      .show()
  }
}
