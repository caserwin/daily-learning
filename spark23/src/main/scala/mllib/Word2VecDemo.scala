package mllib

import org.apache.spark.ml.feature.Word2Vec
import org.apache.spark.sql.{Row, SparkSession}
import scala.collection.mutable.WrappedArray.ofRef

/**
  * Created by yidxue on 2018/12/10
  */
object Word2VecDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("Word2Vec example")
      .config("spark.master", "local[*]")
      .getOrCreate()
    import spark.implicits._
    // Input data
    val documentDF = spark.createDataFrame(Seq(
      "Hi I heard about Hadoop Apache".split(" "),
      "Hi I heard about Spark Apache".split(" "),
      "I wish Java could use case classes".split(" "),
      "Logistic regression models are neat".split(" ")
    ).map(Tuple1.apply)).toDF("text")

    // train word2vec
    val word2Vec = new Word2Vec()
      .setInputCol("text")
      .setOutputCol("result")
      .setVectorSize(3)
      .setMinCount(0)
    val model = word2Vec.fit(documentDF)

    // output word and document vector
    val wordRDD = model.transform(Seq(Seq("Hadoop"), Seq("Spark"), Seq("regression")).toDF("text")).rdd.map {
      r: Row =>
        val word = r.get(0).asInstanceOf[scala.collection.mutable.WrappedArray[ofRef[String]]]
        val vec = r.get(1).asInstanceOf[org.apache.spark.ml.linalg.DenseVector]
        (word, vec.values)
    }
    wordRDD.foreach(x => println(s" ${x._1.mkString(" ")} => ${x._2.mkString(",")}"))

    val docRDD = model.transform(documentDF).rdd.map{
      r: Row =>
        val word = r.get(0).asInstanceOf[scala.collection.mutable.WrappedArray[ofRef[String]]]
        val vec = r.get(1).asInstanceOf[org.apache.spark.ml.linalg.DenseVector]
        (word, vec.values)
    }

    docRDD.foreach(x => println(s" ${x._1.mkString(" ")} => ${x._2.mkString(",")}"))

  }
}
