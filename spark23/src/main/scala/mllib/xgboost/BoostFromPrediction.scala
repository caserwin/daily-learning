package mllib.xgboost

import ml.dmlc.xgboost4j.scala.{DMatrix, XGBoost}

import scala.collection.mutable

object BoostFromPrediction {
  def main(args: Array[String]): Unit = {
    println("start running example to start from a initial prediction")

    val trainMat = new DMatrix("data/xgboost/agaricus.txt.train")
    val testMat = new DMatrix("data/xgboost/agaricus.txt.test")

    val params = new mutable.HashMap[String, Any]()
    params += "eta" -> 1.0
    params += "max_depth" -> 2
    params += "silent" -> 1
    params += "objective" -> "binary:logistic"

    val watches = new mutable.HashMap[String, DMatrix]
    watches += "train" -> trainMat
    watches += "test" -> testMat

    val round = 2
    // train a model
    val booster = XGBoost.train(trainMat, params.toMap, round, watches.toMap)

    val trainPred = booster.predict(trainMat, outPutMargin = true)
    val testPred = booster.predict(testMat, outPutMargin = true)

    trainMat.setBaseMargin(trainPred)
    testMat.setBaseMargin(testPred)

    System.out.println("result of running from initial prediction")
    val booster2 = XGBoost.train(trainMat, params.toMap, 1, watches.toMap, null, null)
  }
}
