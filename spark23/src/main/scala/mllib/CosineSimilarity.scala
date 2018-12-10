package mllib

import scala.collection.mutable.ListBuffer

/**
  * Created by yidxue on 2018/12/10
  */
object CosineSimilarity {

  def main(args: Array[String]): Unit = {

    val wordA = ("Hadoop", Array[Double](-0.04086214303970337,0.028080517426133156,-0.16316691040992737))
    val wordB = ("Spark", Array[Double](0.16520129144191742,0.06437122821807861,0.09267283976078033))
    val wordC = ("regression", Array[Double](0.03680269792675972,0.057698942720890045,-0.022574368864297867))

    val docA = ("Hi I heard about Hadoop Apache", Array[Double](-0.048072672449052334,0.01670865621417761,-0.013722662697546184))
    val docB = ("Hi I heard about Spark Apache", Array[Double](-0.013728766702115536,0.022757108012835182,0.02891729566423843))
    val docC = ("Logistic regression models are neat", Array[Double](0.042249380797147754,0.022417550813406708,-0.013993612676858903))
    val docD = ("I wish Java could use case classes", Array[Double](0.03104841469654015,-0.04865814306374107,0.024822466607604707))


    println(cosvec(wordA._2, wordB._2))
    println(cosvec(wordA._2, wordC._2))
    println(cosvec(wordB._2, wordC._2))

  }

  /**
    * 求向量的模
    */
  def module(vec: Array[Double]): Double = {
    math.sqrt(vec.map(math.pow(_, 2)).sum)
  }

  /**
    * 求两个向量的内积
    */
  def innerProduct(v1: Array[Double], v2: Array[Double]): Double = {
    val listBuffer = ListBuffer[Double]()
    for (i <- v1.indices; j <- 0 to v2.length; if i == j) {
      if (i == j) listBuffer.append(v1(i) * v2(j))
    }
    listBuffer.sum
  }

  /**
    * 求两个向量的余弦
    */
  def cosvec(v1: Array[Double], v2: Array[Double]): Double = {
    val cos = innerProduct(v1, v2) / (module(v1) * module(v2))
    if (cos <= 1) cos else 1.0
  }
}
