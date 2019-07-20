package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.IntegerType

object SparkSQLSortByOtherColDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application")
      .config("spark.master", "local[*]")
      .config("spark.sql.crossJoin.enabled", "true") // 注意，必须有这个配置。
      .getOrCreate()
    import spark.implicits._

    val data = Seq(
      ("id1", 0, 15237),
      ("id1", 1, 7225),
      ("id1", 23, 7747),
      ("id2", 0, 8484),
      ("id2", 1, 9978),
      ("id2", 23, 13518)
    ).toDF("id", "sales", "goods_id")

    val df = data.filter($"sales".cast(IntegerType) > 0)
      .withColumn("collected", collect_list($"goods_id")
        .over(Window.partitionBy("id").orderBy($"sales".desc)))
      .groupBy("id")
      .agg(max($"collected").as("collected"))
    df.show()

    spark.stop()
  }
}
