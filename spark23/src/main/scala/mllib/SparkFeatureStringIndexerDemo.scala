package mllib

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.StringIndexer

object SparkFeatureStringIndexerDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("string-indexer").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    val df = spark.createDataFrame(
      Seq((0, "a"), (1, "b"), (2, "c"), (3, "a"), (4, "a"), (5, "c"))
    ).toDF("id", "category")

    val df1 = spark.createDataFrame(
      Seq((0, "a"), (1, "b"), (2, "c"), (3, "a"), (4, "e"), (5, "f"))
    ).toDF("id", "category")

    val indexer = new StringIndexer()
      .setInputCol("category")
      .setOutputCol("categoryIndex")
      .setHandleInvalid("skip") //skip keep error

    val model = indexer.fit(df)

    val indexed = model.transform(df1)
    indexed.show(false)

    spark.stop()
  }
}
