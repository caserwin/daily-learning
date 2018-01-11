package core

import org.apache.spark.sql.SparkSession

/**
  * User: Erwin
  * Date: 17/12/6 上午10:53
  * Description: 
  */
object SparkSQLLimitDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Spark SQL basic example").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._
    val df = spark.read.json("data/cityinfo.json")

    df.select($"name", $"Cindex").limit(3).show()
  }
}
