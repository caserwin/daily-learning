package core

import org.apache.spark.sql.SparkSession

object SparkSQLDemo2 {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Spark SQL basic example").config("spark.master", "local[*]").getOrCreate()

    val df = spark.read.json("data/cityinfo.json")
    df.show()

    df.rdd.map(x=>x.mkString("\t\t")).repartition(1).saveAsTextFile("output")

  }
}
