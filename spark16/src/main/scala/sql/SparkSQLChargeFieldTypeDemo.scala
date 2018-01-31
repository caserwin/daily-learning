package sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types.IntegerType

/**
  * Created by yidxue on 2018/1/31
  */
object SparkSQLChargeFieldTypeDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val dataSeq1 = Seq(
      (1, "lisi"),
      (1, "lisi"),
      (1, "wangwu"),
      (2, "wangwu")
    )
    val inputDF = sc.parallelize(dataSeq1).toDF("id", "name")

    inputDF.printSchema()
    val inputDF1 = inputDF.withColumn("id", $"id".cast("string"))
    inputDF1.printSchema()
    val inputDF2 = inputDF1.withColumn("id", $"id".cast(IntegerType))
    inputDF2.printSchema()
  }
}
