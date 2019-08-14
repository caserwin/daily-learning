package mllib

import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession

object SparkFeatureVectorAssemblerDemo {
  /**
    * VectorAssembler是一个transformer，将多列数据转化为单列的向量列。
    * https://blog.csdn.net/lichao_ustc/article/details/52688127
    */
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[2]")
      .appName("gbtlr example")
      .getOrCreate()

    val dataset = spark
      .createDataFrame(Seq((0, 18, 12, Vectors.dense(0.0, 10.0, 0.5), 1.0)))
      .toDF("id", "hour", "mobile", "userFeatures", "clicked")
    dataset.show()

    val assembler = new VectorAssembler()
      .setInputCols(Array("hour", "mobile", "userFeatures"))
      .setOutputCol("features")

    val output = assembler.transform(dataset)
    output.show(truncate = false)

    spark.stop()
  }
}
