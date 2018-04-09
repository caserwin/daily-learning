package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

object SparkSQLUDFDemo1 {

  private val isGreen: UserDefinedFunction = udf((color: String) => if (color == "Green") 1 else 0)

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

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
