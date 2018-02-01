package sql

import org.apache.spark.sql.{Row, SparkSession}

/**
  * Spark dataframe with null value to rdd
  */
object SparkSQLNullDemo1 {

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

    val resRDD = inputDF.select($"id", $"name", $"city").na.fill("").rdd.map {
      case Row(id: Integer, name: String, city: String) => (id, name, city)
    }

    for (r <- resRDD.collect()) {
      println(r)
    }
  }
}
