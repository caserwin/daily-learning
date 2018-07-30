package core.save

import org.apache.spark.sql.SparkSession

object SparkSaveDataFrameDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("Simple Application").config("spark.master", "local[*]").getOrCreate()

    val df = spark.sqlContext.read.json("data/cityinfo.json")
    df.show()

    df.rdd.map(x=>x.mkString("\t\t")).repartition(1).saveAsTextFile("output")

  }
}
