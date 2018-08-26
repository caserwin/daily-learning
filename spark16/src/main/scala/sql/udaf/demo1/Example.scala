package sql.udaf.demo1

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types.{DoubleType, StringType}
import org.apache.spark.{SparkConf, SparkContext}

object Example {

  def main(args: Array[String]): Unit = {
    import org.apache.spark.sql.functions._

    val conf = new SparkConf().setAppName("Scala UDAF Example").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    import sqlContext.implicits._
    val input = sc.parallelize(Seq(
      (1, Map("John" -> 12.5, "Alice" -> 5.5)),
      (1, Map("Jim" -> 16.5, "Alice" -> 4.0)),
      (2, Map("John" -> 13.5, "Jim" -> 2.5))
    )).toDF("id", "scoreMap")

    input.show(truncate = false)

    // instantiate a CombineMaps with the relevant types:
    val combineMaps = new CombineMaps[String, Double](StringType, DoubleType, _ + _)
    // groupBy and aggregate
    val result = input.groupBy("id").agg(combineMaps(col("scoreMap")))

    result.show(truncate = false)
    // +---+--------------------------------------------+
    // |id |CombineMaps(scoreMap)                       |
    // +---+--------------------------------------------+
    // |1  |Map(John -> 12.5, Alice -> 9.5, Jim -> 16.5)|
    // |2  |Map(John -> 13.5, Jim -> 2.5)               |
    // +---+--------------------------------------------+
  }
}
