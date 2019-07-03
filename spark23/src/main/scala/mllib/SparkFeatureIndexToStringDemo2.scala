package mllib

import org.apache.spark.ml.feature.{IndexToString, StringIndexer}
import org.apache.spark.sql.SparkSession

object SparkFeatureIndexToStringDemo2 {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("dct").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    val indexed = spark.createDataFrame(Seq(
      (0, 0.0),
      (1, 2.0),
      (2, 1.0),
      (3, 0.0),
      (4, 0.0),
      (5, 1.0)
    )).toDF("id", "categoryIndex")

    val converter = new IndexToString()
      .setInputCol("categoryIndex")
      .setOutputCol("originalCategory")
      .setLabels(Array("a", "b", "c"))

    val converted = converter.transform(indexed)
    converted.show()
  }
}
