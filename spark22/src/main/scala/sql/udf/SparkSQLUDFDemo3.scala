package sql.udf

import org.apache.spark.sql.SparkSession

object SparkSQLUDFDemo3 {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("SQL Application").config("spark.master", "local[*]").getOrCreate()

    val df = spark.read.json("data/temperatures.json")
    df.createOrReplaceTempView("citytemps")

    // Register the UDF with our SQLContext
    spark.udf.register("CTOF", test _)
    spark.sql("SELECT city, CTOF(avgLow) AS avgLowF, CTOF(avgHigh) AS avgHighF FROM citytemps").show()
  }

  def test(degreesCelcius: Double): Double = {
    (degreesCelcius * 9.0 / 5.0) + 32.0
  }
}
