package mllib

import org.apache.spark.ml.feature.StopWordsRemover
import org.apache.spark.sql.SparkSession
/**
  * Created by yidxue on 2018/11/8
  */
object StopWordsRemoverDemo {

  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = SparkSession
      .builder
      .appName("meeting toipic")
      .master("local[*]")
      .getOrCreate()

    val remover = new StopWordsRemover()
      .setInputCol("raw")
      .setOutputCol("filtered")

    val dataSet = spark.createDataFrame(Seq(
      (0, Seq("I", "saw", "the", "red", "baloon")),
      (1, Seq("Mary", "had", "a", "little", "lamb"))
    )).toDF("id", "raw")

    remover.transform(dataSet).show(truncate = false)

    spark.stop()
  }
}
