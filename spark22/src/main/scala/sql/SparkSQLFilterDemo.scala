package sql

import com.jayway.jsonpath.JsonPath
import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/1/29
  */
object SparkSQLFilterDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    // dataframe filter
    val dataSeq1 = Seq(
      (1, "zhangsan", ""),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq1).toDF("id", "name", "city")
    inputDF.filter($"city" !== "").show()

    // rdd filter
    val data = spark.sparkContext.parallelize(Seq(
      """{"message": {"this": 1}}""",
      """{"message": {"this": 2}}"""
    ))
    data.filter(json => jsonFilter(json, "$.message.this", "1")).foreach(println(_))

  }


  def jsonFilter(json: String, label: String, value: String): Boolean = {
    var is = false
    try {
      if (JsonPath.parse(json).read(label).toString.toLowerCase == value) {
        is = true
      }
    } catch {
      case _: Exception =>
    }
    is
  }
}
