package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.{UserDefinedFunction, Window}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StringType

object SparkSQLGroupBySortDemo {
  val merge_array: UserDefinedFunction = udf(mergeArray)

  def mergeArray: Seq[Seq[String]] => Seq[String] = {
    xs => {
      val arr = xs.flatten.distinct
      val goods_confidence_map = arr.map(item => {
        val goods_and_confidence = item.split(":")
        goods_and_confidence(0) -> goods_and_confidence(1).toDouble
      })

      goods_confidence_map.toList.sortBy(-_._2).take(10).map(item => item._1 + ":" + item._2)
    }
  }

  val merge_item: UserDefinedFunction = udf(mergeItem)

  def mergeItem: Seq[String] => String = {
    xs => {
      //      val arr = xs.flatten.distinct
      val goods_confidence_map = xs.map(item => {
        val goods_and_confidence = item.split(":")
        goods_and_confidence(0) -> goods_and_confidence(1).toDouble
      })
      //
      goods_confidence_map.toList.sortBy(-_._2).take(1).toString()
    }
  }

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

//    data.groupBy("col1").agg(merge_array(collect_list("col3"))).show(truncate = false)


    val win = Window.partitionBy("col1").orderBy($"col2".desc, $"col3".desc)
    val firstDF = data.withColumn("rownum", row_number().over(win)).filter("rownum <= 3")
    firstDF.filter($"col2".cast(StringType).=!=("3")).show()

    spark.stop()
  }
}
