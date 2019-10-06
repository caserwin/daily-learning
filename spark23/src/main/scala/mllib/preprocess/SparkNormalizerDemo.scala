package mllib.preprocess

import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.Normalizer

object SparkNormalizerDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[2]").appName("example").getOrCreate()
    val df = spark.createDataFrame(Seq(
      (0, Vectors.dense(1.0, 0.5, -1.0)),
      (1, Vectors.dense(2.0, 1.0, 1.0)),
      (2, Vectors.dense(4.0, 10.0, 2.0))
    )).toDF("id", "features")

    df.show(truncate = false)

    // 正则化每个向量到1阶范数，1阶范数即所有值绝对值之和。
    val normalizer = new Normalizer()
      .setInputCol("features")
      .setOutputCol("normFeatures")
      .setP(1.0)

    val l1NormData = normalizer.transform(df)
    println("Normalized using L^1 norm")
    l1NormData.show(truncate = false)

    // 正则化每个向量到2阶范数，也就是每个维度都除以这个向量2范数长度
    val lInfNormData = normalizer.transform(df, normalizer.p -> 2)
    println("Normalized using L^2 norm")
    lInfNormData.show(truncate = false)

    /**
      * 如下：以第一个元素为例，之所以长这样是因为：
      *
      * 1/sqrt(1^2 +0.5^2 +(-1)^2), 0.5/sqrt(1^2 +0.5^2 +(-1)^2) , -1/sqrt(1^2 +0.5^2 +(-1)^2)
      * +---+--------------+-----------------------------------------------------------+
      * |id |features      |normFeatures                                               |
      * +---+--------------+-----------------------------------------------------------+
      * |0  |[1.0,0.5,-1.0]|[0.6666666666666666,0.3333333333333333,-0.6666666666666666]|
      * |1  |[2.0,1.0,1.0] |[0.8164965809277261,0.4082482904638631,0.4082482904638631] |
      * |2  |[4.0,10.0,2.0]|[0.3651483716701107,0.9128709291752769,0.18257418583505536]|
      * +---+--------------+-----------------------------------------------------------+
      **/

    spark.stop()
  }
}
