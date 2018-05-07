package util

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/4/25
  */
object SparkCreateDataFrameDemo {

  val COUNTRY_MAP = Map(
    "日本" -> "JAPAN",
    "中国" -> "CHINA",
    "ドイツ" -> "GERMANY",
    "台灣" -> "CHINA TAIWAN",
    "美国" -> "UNITED STATES",
    "中非共和国" -> "CENTRAL AFRICAN REPUBLIC",
    "ПЕРУ" -> "PERU",
    "ILHAS VIRGENS BRITÂNICAS" -> "BRITISH VIRGIN ISLANDS",
    "말레이시아" -> "MALAYSIA",
    "핏케언 제도" -> "PITCAIRN ISLANDS",
    "新加坡" -> "SINGAPORE",
    "SÉNÉGAL" -> "SENEGAL",
    "カンボジア" -> "CAMBODIA"
  )

  val mapping: String =
    """|4801,DNAME
       |5101,acwd
       |5106,avwd
       |4901,ixwd
       |5201,akwd
       |4701,esnatech
       |5006,abwd
       |701,dgwd
       |801,diwd
       |901,djwd
       |10202,rvwd""".stripMargin

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
