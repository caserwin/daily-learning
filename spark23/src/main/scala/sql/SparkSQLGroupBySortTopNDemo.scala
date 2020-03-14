package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StringType

object SparkSQLGroupBySortTopNDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application")
      .config("spark.master", "local[*]")
      .getOrCreate()
    import spark.implicits._

    val data = Seq(
      ("id1", 1, 1, Seq("a:1", "b:2")),
      ("id1", 2, 1, Seq("c:2", "b:3")),
      ("id2", 3, 1, Seq("a:1", "b:4")),
      ("id2", 3, 0, Seq("c:2", "d:3")),
      ("id2", 4, 0, Seq("c:2", "d:3"))
    ).toDF("col1", "col2", "col3", "col4")

    val win = Window.partitionBy("col1").orderBy($"col2".desc, $"col3".desc)
    val firstDF = data.withColumn("rownum", row_number().over(win)).filter("rownum <= 3").drop("rownum")
    firstDF.filter($"col2".cast(StringType).=!=("3")).show()

    spark.stop()
  }
}