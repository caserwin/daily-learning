package mllib

import org.apache.spark.ml.feature.{RegexTokenizer, Tokenizer}
import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/11/8
  */
object TokenizerDemo {

  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = SparkSession
      .builder
      .appName("meeting toipic")
      .master("local[*]")
      .getOrCreate()

    val sentenceDataFrame = spark.createDataFrame(Seq(
      (1, "I wish Java could use case classes"),
      (2, "Logistic  regression , models are neat"),
      (3, "Test Recording-20181101 1556-1"),
      (4, "Follow-up from FY19 strategy & planning meeting - Part 2-20181101 2203-1"),
      (5, "685531403 - SD-WAN Issues..-20181101 1503-1")
    )).toDF("label", "sentence")

    val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
    val regexTokenizer = new RegexTokenizer()
      .setInputCol("sentence")
      .setOutputCol("words")
      .setPattern("[^a-zA-Z_0-9-]+")

    val tokenized = tokenizer.transform(sentenceDataFrame)
    tokenized.select("words", "label").take(10).foreach(println)
    val regexTokenized = regexTokenizer.transform(sentenceDataFrame)
    regexTokenized.select("words", "label").take(10).foreach(println)

    spark.stop()
  }
}
