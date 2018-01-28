package sql.udaf.demo2

import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

object Test {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("Scala UDAF Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    // define UDAF
    val customMean = new CustomMean()

    // create test dataset
    val data = (1 to 1000).map{x:Int => x match {
      case t if t <= 500 => Row("A", t.toDouble)
      case t => Row("B", t.toDouble)
    }}

    // create schema of the test dataset
    val schema = StructType(Array(
      StructField("key", StringType),
      StructField("value", DoubleType)
    ))

    // construct data frame
    val rdd = sc.parallelize(data)
    val df = sqlContext.createDataFrame(rdd, schema)

    df.show(30, truncate = false)

    // Calculate average value for each group
    df.groupBy("key").agg(customMean(df.col("value")).as("custom_mean"), avg("value").as("avg")).show()
  }
}
