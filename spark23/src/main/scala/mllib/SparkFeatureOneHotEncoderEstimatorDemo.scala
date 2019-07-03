package mllib

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.feature.{OneHotEncoderEstimator, StringIndexer, VectorAssembler}
import org.apache.spark.sql.SparkSession

object SparkFeatureOneHotEncoderEstimatorDemo {

  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = SparkSession
      .builder
      .appName("meeting toipic")
      .master("local[*]")
      .getOrCreate()

    val dataset = spark.createDataFrame(Seq(
      (60, 170, "F", "长春"),
      (45, 163, "M", "长春"),
      (80, 183, "F", "沈阳"),
      (70, 175, "F", "大连"),
      (52, 167, "M", "哈尔滨")
    )).toDF("weight", "height", "sex", "address")

    val fields = Array("sex", "address")
    // index 编码
    val indexers = fields.map(name => new StringIndexer().setInputCol(name).setOutputCol(name + "_1"))
    val pipeline = new Pipeline().setStages(indexers)
    val data = pipeline.fit(dataset).transform(dataset)

    // one -hot 编码
    val encoder = new OneHotEncoderEstimator().setInputCols(fields.map(x => x + "_1")).setOutputCols(fields.map(x => x + "_2"))
    val model = encoder.fit(data)

    val encoded = model.transform(data)
    encoded.show()

    // 拼接特征
    val assembler = new VectorAssembler().setInputCols(Array("weight", "height", "sex_2", "address_2")).setOutputCol("features")
    val output = assembler.transform(encoded)
    output.show(truncate = false)

    spark.stop()
  }
}
