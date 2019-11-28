package sql

import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

object SparkSQLAnyCols2DFDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()

    val dataSeq1 = Seq(
      List("HangZhou", "1.7", "CN", "aaa"),
      List("NewYork", "1.7", "US", "downloadstart"),
      List("Halifax", "3.0", "US", "downloadend"),
      List("ShangHai", "2.2", "CN", "aaa"),
      List("NanJing", "1.0", "CN", "aaa")
    ).map { x => Row(x: _*) }

    val rdd = spark.sparkContext.makeRDD(dataSeq1)

    val schema = StructType("program_id, user_count, gender_0, gender_1".split(",")
      .map(fieldName => StructField(fieldName.trim, StringType, nullable = true)))

    val df = spark.createDataFrame(rdd, schema)
    df.show()

    spark.stop()
  }
}
