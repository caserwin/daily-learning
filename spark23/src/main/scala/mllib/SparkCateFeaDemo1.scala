package mllib

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.feature.{OneHotEncoderEstimator, StringIndexer}
import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2020-08-30
  */
object SparkCateFeaDemo1 {

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder().appName("demo").master("local[*]").getOrCreate()
    import spark.implicits._

    val df = Seq((0, "a", 1), (1, "b", 2), (2, "c", 3), (3, "a", 4), (4, "a", 4), (5, "c", 3)).toDF("id", "category1", "category2")

    val indexer = new StringIndexer().setInputCol("category1").setOutputCol("category1Index")
    val encoder = new OneHotEncoderEstimator()
      .setInputCols(Array(indexer.getOutputCol, "category2"))
      .setOutputCols(Array("category1Vec", "category2Vec"))

    val pipeline = new Pipeline().setStages(Array(indexer, encoder))
    pipeline.fit(df).transform(df).show(truncate = false)

    spark.stop()
  }
}
