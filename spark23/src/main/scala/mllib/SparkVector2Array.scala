package mllib

import org.apache.spark.ml.feature.OneHotEncoderEstimator
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, udf}

object SparkVector2Array {

  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = SparkSession
      .builder
      .appName("meeting toipic")
      .master("local[*]")
      .getOrCreate()

    val df = spark.createDataFrame(Seq(
      (0.0, 1.0),
      (1.0, 0.0),
      (2.0, 1.0),
      (0.0, 2.0),
      (0.0, 1.0),
      (2.0, 0.0)
    )).toDF("categoryIndex1", "categoryIndex2")

    val encoder = new OneHotEncoderEstimator()
      .setInputCols(Array("categoryIndex1", "categoryIndex2"))
      .setOutputCols(Array("categoryVec1", "categoryVec2"))
    val model = encoder.fit(df)

    import org.apache.spark.ml.linalg.SparseVector
    val toArr: Any => Array[Double] = _.asInstanceOf[SparseVector].toArray
    val toArrUdf = udf(toArr)

    val encoded = model.transform(df)
      .withColumn("categoryVec1_arr",toArrUdf(col("categoryVec1")))
      .withColumn("categoryVec2_arr",toArrUdf(col("categoryVec2")))

    encoded.show()
    //    encoded.printSchema()

    spark.stop()
  }
}
