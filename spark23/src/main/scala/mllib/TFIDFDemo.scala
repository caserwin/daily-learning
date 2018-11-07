package mllib

import org.apache.spark.ml.feature._
import org.apache.spark.ml.linalg.SparseVector
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf
import org.apache.spark.sql.{Row, SparkSession}

/**
  * Created by yidxue on 2018/11/7
  */
object TFIDFDemo {

  val vecToSeq: UserDefinedFunction = udf((v: Vector) => v.toArray)
  val topN = 3

  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = SparkSession
      .builder
      .appName("meeting toipic")
      .master("local[*]")
      .getOrCreate()

    val sentenceData = spark.createDataFrame(Seq(
      (0, "I heard about Spark and I love Spark"),
      (1, "I wish Java could use case classes"),
      (2, "Logistic regression models are neat")
    )).toDF("label", "sentence")

    val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
    val wordsData = tokenizer.transform(sentenceData)

    val cvModel: CountVectorizerModel = new CountVectorizer().setInputCol("words").setOutputCol("rawFeatures").fit(wordsData)
    val featurizedData = cvModel.transform(wordsData)

    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features").fit(featurizedData)
    val rescaledData = idf.transform(featurizedData)
    val resDF = rescaledData.select("label", "features")

    resDF.rdd.map {
      row: Row => {
        val vec = row.get(1).asInstanceOf[SparseVector]
        val words = vec.indices.map(x => cvModel.vocabulary(x))
        val map = words.zip(vec.values).sortBy(_._2).reverse.take(topN).mkString(",")
        (row.get(0), map)
      }
    }.foreach(println(_))

    spark.stop()
  }
}
