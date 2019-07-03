package mllib

import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.{HashingTF, Tokenizer}
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.sql.{Row, SparkSession}

object SparkCategoryPipelineDemo {
  /**
    * https://zhuanlan.zhihu.com/p/24311565
    * http://dblab.xmu.edu.cn/blog/1261-2/
    *
    * 我们可以看到每一个单词被哈希成了一个不同的索引值。以”I heard about Spark and I love Spark”为例，
    * 输出中：
    * 1000代表哈希表的桶数，
    * [105,240,329,333,357,777] 分别代表着“spark, i, heard, about, and, love”的哈希值，
    * [2.0,1.0,1.0,1.0,1.0,1.0] 为对应单词的出现次数。
    */
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[2]").appName("example").getOrCreate()

    val training = spark.createDataFrame(Seq(
      (0L, "a b c d e spark", 1.0),
      (1L, "b d", 0.0),
      (2L, "spark spark f g h", 1.0),
      (3L, "hadoop mapreduce", 0.0),
      (4L, "I heard about Spark and love Spark", 1.0)
    )).toDF("id", "text", "label")

    // Configure an ML pipeline, which consists of three stages: tokenizer, hashingTF, and lr.
    val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
    val hashingTF = new HashingTF().setNumFeatures(1000).setInputCol(tokenizer.getOutputCol).setOutputCol("features")
    val lr = new LogisticRegression().setMaxIter(10).setRegParam(0.01)
    val pipeline = new Pipeline().setStages(Array(tokenizer, hashingTF, lr))

    // Fit the pipeline to training documents.
    val model = pipeline.fit(training)

    model.transform(training).show(truncate = false)

    // Now we can optionally save the fitted pipeline to disk
    model.write.overwrite().save("data/model/spark-logistic-regression-model")

    // We can also save this unfit pipeline to disk
    pipeline.write.overwrite().save("data/model/unfit-lr-model")

    // And load it back in during production
    val sameModel = PipelineModel.load("data/model/spark-logistic-regression-model")

    // Prepare test documents, which are unlabeled (id, text) tuples.
    val test = spark.createDataFrame(Seq(
      (5L, "spark i j k"),
      (6L, "l m n"),
      (7L, "mapreduce spark"),
      (8L, "apache hadoop")
    )).toDF("id", "text")

    // Make predictions on test documents.
    model.transform(test).select("id", "text", "probability", "prediction").collect()
      .foreach { case Row(id: Long, text: String, prob: Vector, prediction: Double) =>
        println(s"($id, $text) --> prob=$prob, prediction=$prediction")
      }

    spark.stop()
  }
}
