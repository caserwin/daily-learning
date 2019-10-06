package mllib

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.linalg.{Vector, Vectors}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD


object SparkVectorDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[2]").appName("example").getOrCreate()

    // 创建dense vector
    val dv: Vector = Vectors.dense(1.0, 0.0, 3.0)
    println(dv.toArray.mkString(","))
    println(dv.toDense)
    println(dv.toSparse)

    // 创建sparse vector
    val sv1: Vector = Vectors.sparse(3, Array(0, 2), Array(1.0, 3.0))
    //    val sv2: Vector = Vectors.sparse(3, Seq((0, 1.0), (2, 3.0)))
    println("=======================")
    println(sv1.toArray.mkString(","))
    println(sv1.toDense)
    println(sv1.toSparse)

    // 创建dense vector 数据集
    val df = spark.createDataFrame(Seq(
      (0, Vectors.dense(1.0, 0.5, -1.0)),
      (1, Vectors.dense(2.0, 1.0, 1.0)),
      (2, Vectors.dense(4.0, 10.0, 2.0))
    )).toDF("id", "features")

    df.show(truncate = false)

    // 创建dense vector 数据集
    val examples: RDD[LabeledPoint] = MLUtils.loadLibSVMFile(spark.sparkContext, "data/mllib/sample_libsvm_data.txt")
    println(examples.take(10).foreach(x => println(x)))

    spark.stop()
  }
}
