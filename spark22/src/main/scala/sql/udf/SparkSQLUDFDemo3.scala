package sql.udf

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/5/2
  */
object SparkSQLUDFDemo3 {

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("x").config("spark.master", "local[*]").getOrCreate()

    val df = sparkSession.read.json("data/temperatures.json")
    df.createOrReplaceTempView("citytemps")

    // Register the UDF with our SQLContext
    sparkSession.udf.register("CTOF", test _)
    sparkSession.sql("SELECT city, CTOF(avgLow) AS avgLowF, CTOF(avgHigh) AS avgHighF FROM citytemps").show()
  }

  def test(degreesCelcius: Double): Double = {
    (degreesCelcius * 9.0 / 5.0) + 32.0
  }
}
