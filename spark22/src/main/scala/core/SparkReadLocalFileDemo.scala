package util

import org.apache.spark.sql.SparkSession

/**
  * User: Erwin
  * Date: 17/11/7 下午12:43
  * Description: 
  */
object SparkReadLocalFileDemo {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("Spark SQL basic example").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val peopleDF = spark.sparkContext
      .textFile("spark22/src/main/resources/people.txt").map(_.split(","))
      .map(attributes => (attributes(0), attributes(1).trim.toInt))
      .toDF("id", "name")

    peopleDF.show()
  }
}
