package sql

import com.jayway.jsonpath.JsonPath
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/1/29
  */
object SparkSQLFilterDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    // dataframe filter
    val dataSeq1 = Seq(
      ("1", "zhangsan", ""),
      ("2", "lisi", "beijing"),
      ("3", "wangwu", "shanghai")
    )
    val inputDF = sc.parallelize(dataSeq1).toDF("id", "name", "city")
    // notice 注意这里，字符类型直接和数字类型比大小也没报错。
    inputDF.filter($"id" < 2).show()
    inputDF.filter($"city" !== "").show()
    inputDF.where($"city" !== "").show()

    // rdd filter
    val data = sc.parallelize(Seq(
      """{"message": {"this": 1}}""",
      """{"message": {"this": 2}}"""
    ))
    data.filter(json => jsonFilter(json, "$.message.this", "1")).foreach(println(_))

    sc.stop()
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
