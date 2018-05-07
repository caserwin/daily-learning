package sql.udf

import org.apache.spark.sql.SparkSession

object SparkSQLUDFDemo2 {

  def main(args: Array[String]) {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()

    val df = spark.read.json("data/temperatures.json")
    df.createOrReplaceTempView("citytemps")

    // Register the UDF with our SQLContext
    spark.udf.register("CTOF", (degreesCelcius: Double) => (degreesCelcius * 9.0 / 5.0) + 32.0)
    spark.sql("SELECT city, CTOF(avgLow) AS avgLowF, CTOF(avgHigh) AS avgHighF FROM citytemps").show()
  }
}
