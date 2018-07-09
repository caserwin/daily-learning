package sql

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}

/**
  * Created by yidxue on 2018/7/9
  */
object SparkSQLCreateEmptyDFForLoopDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    //initialize DF
    val schema = StructType(
      StructField("col1", StringType, nullable = true) ::
        StructField("col2", StringType, nullable = true) ::
        StructField("col3", StringType, nullable = true) :: Nil)
    var initialDF = spark.createDataFrame(spark.sparkContext.emptyRDD[Row], schema)

    val fruits = List("apple", "orange", "melon")

    for (x <- fruits) {
      initialDF = initialDF.union(Seq(("aaa", "bbb", x)).toDF)
    }
    initialDF.show()
  }
}
