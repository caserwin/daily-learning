package mllib.xgboost

import ml.dmlc.xgboost4j.scala.spark.XGBoostClassifier
import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}
import org.apache.spark.ml.linalg.{Vector, Vectors}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}

/**
  * Created by yidxue on 2020-05-10
  */
object XGBoostSparkTrainDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("get feature").master("local[*]").getOrCreate()

    val schema = new StructType(Array(
      StructField("sepal length", DoubleType, nullable = true),
      StructField("sepal width", DoubleType, nullable = true),
      StructField("petal length", DoubleType, nullable = true),
      StructField("petal width", DoubleType, nullable = true),
      StructField("class", StringType, nullable = true)))
    val rawInput = spark.read.schema(schema).csv("data/iris.data")

    val stringIndexer = new StringIndexer().
      setInputCol("class").
      setOutputCol("classIndex").
      fit(rawInput)
    val labelTransformed = stringIndexer.transform(rawInput).drop("class")

    val vectorAssembler = new VectorAssembler().
      setInputCols(Array("sepal length", "sepal width", "petal length", "petal width")).
      setOutputCol("features")
    val xgbInput = vectorAssembler.transform(labelTransformed).select("features", "classIndex")

    val xgbParam = Map(
      "eta" -> 0.1f,
      "missing" -> -999,
      "objective" -> "multi:softprob",
      "num_class" -> 3,
      "num_round" -> 100,
      "num_workers" -> 2)

    val xgbClassifier = new XGBoostClassifier(xgbParam).
      setFeaturesCol("features").
      setLabelCol("classIndex")
    val xgbClassificationModel = xgbClassifier.fit(xgbInput)

    //    val features: linalg.Vector = xgbInput.head().getAs[org.apache.spark.ml.linalg.Vector]("features")
    val features0: Vector = Vectors.sparse(4, Array(0, 1, 2, 3), Array(5.4, 3.9, 1.3, 1.4))
    val features1: Vector = Vectors.sparse(4, Array(0, 1, 2, 3), Array(5.7, 3.0, 4.2, 1.2))
    val features2: Vector = Vectors.sparse(4, Array(0, 1, 2, 3), Array(6.9, 3.1, 5.1, 2.3))

    // 离线预测
    val df = spark.createDataFrame(Seq(
      (1, features0),
      (2, features1),
      (3, features2)
    )).toDF("id", "features")
    xgbClassificationModel.transform(df.select("features")).select("probability", "prediction").show(10, truncate = false)

    // 实时预测
    println(xgbClassificationModel.predict(features0))
    println(xgbClassificationModel.predict(features1))
    println(xgbClassificationModel.predict(features2))

    // 存模型
    val xgbClassificationModelPath = "data/model/xgbClassificationModel"
    xgbClassificationModel.nativeBooster.saveModel(xgbClassificationModelPath)

    spark.stop()
  }
}
