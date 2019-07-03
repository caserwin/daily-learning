package sql.createDF

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StringType, StructField, StructType}

/**
  * Created by yidxue on 2019/5/21
  */
object SparkSQLSchemaJsonDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder.
      appName("SQL Application")
      .config("spark.master", "local[*]")
      .getOrCreate()

//    spark.sqlContext.sql("set spark.sql.caseSensitive=true")

    val inputSchema = StructType(
      Array(
        StructField("siteId", StringType, nullable = true),
        StructField("eventType", StringType, nullable = true),
        StructField("timeStamp", StringType, nullable = true),
        StructField("gid", StringType, nullable = true),
        StructField("userName", StringType, nullable = true)
      )
    )

    val recordsDF = spark.read.schema(inputSchema).json("./data/test.data").na.fill("")

    recordsDF.show()

    spark.stop()
  }
}
