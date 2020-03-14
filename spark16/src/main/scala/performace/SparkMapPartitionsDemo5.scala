package performace

import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/10/22
  * https://stackoverflow.com/questions/45648913/map-partition-iterator-return
  */
object SparkMapPartitionsDemo5 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val pNum = args(0).toInt
    val userDF = sqlContext.sql(s"select '1' as id, '2' as uid, '3' as imei, '4' as idfa from TB limit 10000")

    val resRdd = userDF.repartition(pNum).rdd.mapPartitions(
      iter => {
        val s: String = "str1"
        for (cur <- iter) yield {
          Row(cur(0), cur(1), cur(2), cur(3), s)
        }
      })

    val schema = StructType("a,b,c,d,e".split(",")
      .map(fieldName => StructField(fieldName.trim, StringType, nullable = true)))

    val ruleDF = sqlContext.createDataFrame(resRdd, schema)
    ruleDF.show(100, truncate = false)

    sc.stop()
  }
}