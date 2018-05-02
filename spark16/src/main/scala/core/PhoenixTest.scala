package core

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

/**
  * Created by yidxue on 2018/4/25
  */
object PhoenixTest {

  val COUNTRY_MAP = Map(
    "日本" -> "JAPAN",
    "中国" -> "CHINA",
    "ドイツ" -> "GERMANY",
    "台灣" -> "CHINA TAIWAN",
    "美国" -> "UNITED STATES",
    "대한민국" -> "KOREA",
    "ÖSTERREICH" -> "AUSTRIA",
    "ESPAÑA" -> "SPAIN",
    "中非共和国" -> "CENTRAL AFRICAN REPUBLIC",
    "ПЕРУ" -> "PERU",
    "ILHAS VIRGENS BRITÂNICAS" -> "BRITISH VIRGIN ISLANDS",
    "말레이시아" -> "MALAYSIA",
    "瑞士" -> "SWITZERLAND",
    "ジブチ" -> "DJIBOUTI",
    "핏케언 제도" -> "PITCAIRN ISLANDS",
    "新加坡" -> "SINGAPORE",
    "SÉNÉGAL" -> "SENEGAL",
    "カンボジア" -> "CAMBODIA"
  )


  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val inputDF = sc.parallelize(COUNTRY_MAP.map(x => (x._1, x._2)).toSeq).toDF("name", "age")
    inputDF.show()

    val countryMap = inputDF.rdd.collect().map(x => x.get(0) -> x.get(1)).toMap
    val countryMapBroadcast = sc.broadcast(countryMap)
    println(countryMap)

    countryMapBroadcast.value.foreach(println(_))
  }
}
