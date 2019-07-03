package mllib.textming

import org.apache.spark.ml.feature.{CountVectorizer, CountVectorizerModel, IDF, RegexTokenizer}
import org.apache.spark.ml.linalg.SparseVector
import org.apache.spark.sql.{Row, SparkSession}

/**
  * Created by yidxue on 2018/11/7
  */
object SparkTFIDFForExtractKeyWordsDemo {

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
      (2, "Logistic regression , models are neat")
    )).toDF("label", "sentence")

    val regexTokenizer = new RegexTokenizer().setInputCol("sentence").setOutputCol("words").setPattern("\\W+")
    //    val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
    val wordsData = regexTokenizer.transform(sentenceData)
    wordsData.show(truncate = false)

    val cvModel: CountVectorizerModel = new CountVectorizer().setInputCol("words").setOutputCol("rawFeatures").fit(wordsData)
    val featurizedData = cvModel.transform(wordsData)

    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features").fit(featurizedData)
    val rescaledData = idf.transform(featurizedData)
    val resDF = rescaledData.select("label", "features")

    resDF.rdd.map {
      row: Row => {
        val vec = row.get(1).asInstanceOf[SparseVector]
        val words = vec.indices.map(x => cvModel.vocabulary(x))
        val map = words.zip(vec.values.map(x => x.formatted("%.2f"))).sortBy(_._2).reverse.take(topN).mkString(",")
        (row.get(0), map)
      }
    }.foreach(println(_))

    spark.stop()
  }
}
