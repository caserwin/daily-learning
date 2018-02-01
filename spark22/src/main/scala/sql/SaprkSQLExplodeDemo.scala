package sql

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/1/30
  */
object SaprkSQLExplodeDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      ("yidxue", 20, "aa bb"),
      ("erwin", 20, "aa cc"),
      ("yuyi", 20, "bb cc")
    )

    val inputDF = spark.sparkContext.parallelize(dataSeq1).toDF("name", "age", "interest")

    inputDF.show()

    inputDF.explode("interest", "interests") { interest: String => interest.split("\\s+") }
      .select(
        $"name",
        $"age",
        $"interests"
      ).show()
  }
}
