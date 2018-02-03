package sql.datasource

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/2/1
  */
object SparkSQLJsonDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()

    val peopleDF = spark.read.format("json").load("spark-examples/src/main/resources/people.json")
    val peopleDF1 = spark.read.json("spark-examples/src/main/resources/people.json")


    peopleDF.show()
    peopleDF1.show()

  }
}
