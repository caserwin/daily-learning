package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StringType, StructField, StructType}

/**
  * Created by yidxue on 2018/4/20
  */
object SparkSQLSampleDemo {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      ("1", "lisi"),
      ("2", "lisi"),
      ("1", "wangwu"),
      ("2", "wangwu"),
      ("3", "wangwu"),
      ("4", "wangwu")
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq1, 2).toDF("id", "name")

    val res = inputDF.rdd.sample(withReplacement = false, 0.5, 5)

    val columns = "ID, NAME"
    val schema = StructType(columns.split(",").map(fieldName => StructField(fieldName.trim, StringType, nullable = true)))
    val jmtJoinMethodDF = spark.sqlContext.createDataFrame(res, schema)

    jmtJoinMethodDF.show()
  }
}
