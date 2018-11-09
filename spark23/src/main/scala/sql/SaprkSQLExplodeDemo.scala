package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/**
  * Created by yidxue on 2018/1/30
  */
object SaprkSQLExplodeDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      ("yidxue", 20, "aa bb", List(("a", 1), ("a", 2), ("b", 2))),
      ("erwin", 20, "aa cc", List(("b", 1), ("c", 1))),
      ("yuyi", 20, "bb cc", List(("b", 1), ("c", 1)))
    )

    val inputDF = spark.sparkContext.parallelize(dataSeq1).toDF("name", "age", "interest", "other")

    inputDF.show()

    inputDF.explode("interest", "interests") { interest: String => interest.split("\\s+") }.select($"name", $"age", $"interests").show()
    inputDF.withColumn("interests", explode(split($"interest", "\\s+"))).select($"name", $"age", $"interests").show()
    inputDF.withColumn("other", explode($"other")).select($"name", $"age", $"other").show()

    spark.stop()
  }
}
