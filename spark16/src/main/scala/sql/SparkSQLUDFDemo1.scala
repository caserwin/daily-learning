package sql

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions.{udf, when}
import org.apache.spark.{SparkConf, SparkContext}

object SparkSQLUDFDemo1 {

  private val isGreen = udf((color: String) => if (color == "Green") 1 else 0)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val df = Seq("Red", "Green", "Blue").map(Tuple1.apply).toDF("color")
    df.show()

    // method1
    df.withColumn("Green_Ind", isGreen($"color")).show()

    // method2
    df.select(
      $"color",
      isGreen($"color").alias("Green_Ind")
    ).show()

    df.select(
      isGreen($"color").alias("color")
    ).show()

  }
}
