package mllib.dt

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 决策树分类
  */
object DTdemo1 {

  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("DecisionTree").setMaster("local")
    val sc = new SparkContext(conf)
    Logger.getRootLogger.setLevel(Level.WARN)

    //训练数据
    val data1 = sc.textFile("data/dt/Tree1.txt")
    //测试数据
    val data2 = sc.textFile("data/dt/Tree2.txt")

    //转换成向量
    val tree1 = data1.map { line =>
      val parts = line.split(',')
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
    }

    val tree2 = data2.map { line =>
      val parts = line.split(',')
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
    }

    tree2.foreach(x => println(x))


//    //赋值
//    val (trainingData, testData) = (tree1, tree2)
//
//    //分类
//    val numClasses = 2
//    val categoricalFeaturesInfo = Map[Int, Int]()
//    val impurity = "gini"
//
//    //最大深度
//    val maxDepth = 5
//    //最大分支
//    val maxBins = 32
//
//    //模型训练
//    val model = DecisionTree.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
//      impurity, maxDepth, maxBins)
//
//    //模型预测
//    val labelAndPreds = testData.map { point =>
//      val prediction = model.predict(point.features)
//      (point.label, prediction)
//    }
//
//    //测试值与真实值对比
//    val print_predict = labelAndPreds.take(15)
//    println("label" + "\t" + "prediction")
//    for (i <- print_predict.indices) {
//      println(print_predict(i)._1 + "\t" + print_predict(i)._2)
//    }
//
//    //树的错误率
//    val testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / testData.count()
//    println("Test Error = " + testErr)
//    //打印树的判断值
//    println("Learned classification tree model:\n" + model.toDebugString)

  }
}
