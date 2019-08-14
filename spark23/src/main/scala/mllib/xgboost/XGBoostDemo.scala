package mllib.xgboost

import ml.dmlc.xgboost4j.scala.spark.XGBoostClassifier
import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}
//    import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

import org.apache.spark.ml.Pipeline
import org.apache.spark.sql.SparkSession

object XGBoostDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[2]")
      .appName("gbtlr example")
      .getOrCreate()

    val startTime = System.currentTimeMillis()

    val dataset = spark.read.option("header", "true").option("inferSchema", "true")
      .option("delimiter", ";").csv("resource/data/source/bank-full.csv")

    val columnNames = Array("job", "marital", "education",
      "default", "housing", "loan", "contact", "month", "poutcome", "y")
    val indexers = columnNames.map(name => new StringIndexer()
      .setInputCol(name).setOutputCol(name + "_index"))
    val pipeline = new Pipeline().setStages(indexers)
    val data1 = pipeline.fit(dataset).transform(dataset).drop(columnNames: _*)
    //    pipeline.save("model")

    val data2 = data1.withColumnRenamed("y_index", "label")

    val assembler = new VectorAssembler()
    assembler.setInputCols(Array("age", "job_index", "marital_index",
      "education_index", "default_index", "balance", "housing_index",
      "loan_index", "contact_index", "day", "month_index", "duration",
      "campaign", "pdays", "previous", "poutcome_index"))
    assembler.setOutputCol("features")

    val data3 = assembler.transform(data2)
    val data4 = data3.randomSplit(Array(4, 1))

    val xgbInput = data4(0).select("features", "label")
    xgbInput.show(100, truncate = false)
    val booster = new XGBoostClassifier(
      Map(
        "eta" -> 0.1f,
        "max_depth" -> 2,
        "objective" -> "binary:logistic",
        "num_round" -> 100,
        "num_workers" -> 2
      )
    )
    booster.setFeaturesCol("features")
    booster.setLabelCol("label")

    val xgbClassificationModel = booster.fit(xgbInput)



    //    println(xgbClassificationModel.getFeaturesCol)
    println(xgbClassificationModel.nativeBooster.getFeatureScore())


    //    // Batch Prediction
    //    val results = xgbClassificationModel.transform(data4(1))
    //    results.show(100)
    //
    //    // Single instance prediction
    //    val features = data4(1).head().getAs[Vector]("features")
    //    val result = xgbClassificationModel.predict(features)
    //    println(result)

    //    val evaluator = new BinaryClassificationEvaluator()
    //    evaluator.setLabelCol("classIndex")
    //    val accuracy = evaluator.evaluate(result)


  }
}