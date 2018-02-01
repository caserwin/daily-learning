package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.when

object SparkSQLWhenCaseDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val df = Seq("Red", "Green", "Blue").map(Tuple1.apply).toDF("color")
    df.show()

    df.withColumn("Green_Ind", when($"color" === "Green", 1).otherwise(0)).show()
  }
}
