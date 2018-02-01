package sql

import org.apache.spark.sql.{Row, SparkSession}

/**
  * Spark dataframe with null value to rdd
  */
object SparkSQLNullDemo2 {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq = Seq(
      (1, "zhangsan", null),
      (1, "lisi", "33"),
      (3, "wangwu", "shanghai")
    )

    val inputDF = spark.sparkContext.parallelize(dataSeq).toDF("id", "name", "city")
    inputDF.printSchema()

    val resRDD = inputDF.select($"id", $"name", $"city").rdd.map {
      r: Row => (r.getInt(0), r.getString(1), r.getString(2))
    }

    resRDD.foreach(println(_))
  }
}
