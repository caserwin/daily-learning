package sql.createDF

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/4/25
  */
object SparkSQLCreateDFDemo2 {

  val COUNTRY_MAP = Map(
    "日本" -> "JAPAN",
    "中国" -> "CHINA",
    "台湾" -> "CHINA TAIWAN",
    "美国" -> "UNITED STATES",
    "新加坡" -> "SINGAPORE"
  )

  val mapping: String =
    """|4801,xxx
       |5101,yyy
       |5106,fff
       |4901,rrr
       |10202,sss""".stripMargin

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("Simple Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    // map to dataframe
    val inputDF = spark.sparkContext.parallelize(COUNTRY_MAP.map(x => (x._1, x._2)).toSeq).toDF("name", "age")
    inputDF.show()

    // string to dataframe
    val inputDF1 = spark.sparkContext.parallelize(mapping.split("\\n").map(x => x.split(",")).map(arr => (arr(0), arr(1))).toSeq).toDF("CLUSTERID", "CLUSTERNAME")
    inputDF1.show()

    // broadcast dict
    val countryMap = inputDF.rdd.collect().map(x => x.get(0) -> x.get(1)).toMap
    val countryMapBroadcast = spark.sparkContext.broadcast(countryMap)
    println(countryMap)

    countryMapBroadcast.value.foreach(println(_))
  }
}
