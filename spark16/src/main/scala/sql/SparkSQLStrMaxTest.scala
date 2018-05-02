package sql

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._
import org.apache.spark.{SparkConf, SparkContext}


object SparkSQLStrMaxTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Scala SQL Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val dataSeq = Seq(
      (1, "zhangsan", "WebEx/91400.112 CFNetwork/811.5.4 Darwin/16.6.0"),
      (1, "zhangsan", "WebEx/91400.112 CFNetwork/811.5.4 Darwin/16.6.0"),
      (1, "zhangsan", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko"),
      (2, "zhangsan1", "WbxTPAgent, wbxtpgw"),
      (2, "zhangsan", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
    )

    val input = sc.parallelize(dataSeq).toDF("id", "name", "eventtype")
    input.printSchema()

    input.withColumn("name", upper($"name")).filter(!(upper($"name") === "ZHANGSAN1")).show()
    input.groupBy("id").agg(max("eventtype")).show(truncate = false)
  }
}
