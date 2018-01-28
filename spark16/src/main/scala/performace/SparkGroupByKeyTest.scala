package sparkPerformace

import org.apache.spark.{SparkConf, SparkContext}

object SparkGroupByKeyTest {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val words = Array("one", "two", "two", "three", "three", "three")
    val wordPairsRDD = sc.parallelize(words).map(word => (word, 1))

    val wordCountsWithReduce = wordPairsRDD
      .reduceByKey(_ + _)
      .collect()

    val wordCountsWithGroup = wordPairsRDD
      .groupByKey()

    wordCountsWithGroup.collect()
  }
}
