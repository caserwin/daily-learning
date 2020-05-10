package mllib.xgboost

import ml.dmlc.xgboost4j.scala.{DMatrix, XGBoost}

import scala.collection.mutable

object XGBoostSimpleTrainDemo2 {

  def main(args: Array[String]): Unit = {
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

    val round = 3
    val booster = XGBoost.train(trainMat, params.toMap, round, watches.toMap)

    // predict using first 2 tree
    val leafIndex = booster.predictLeaf(testMat, 2)
    for (leafs <- leafIndex) {
      println(java.util.Arrays.toString(leafs))
    }

    // predict all trees
    val leafIndex2 = booster.predictLeaf(testMat, 0)
    for (leafs <- leafIndex2) {
      println(java.util.Arrays.toString(leafs))
    }
  }
}
