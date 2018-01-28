package performace

import org.apache.spark.{SparkConf, SparkContext}

/**
  * User: Erwin
  * Date: 17/11/15 上午10:09
  * Description: 
  */
object SparkGroupByKey {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val words = Array("one", "two", "two", "three", "three", "three")
    val wordPairsRDD = sc.parallelize(words).map(word => (word, 1))
    val wordCountsWithGroup = wordPairsRDD.groupByKey().map(t => (t._1, t._2.sum)).collect()

    wordCountsWithGroup.foreach(println(_))
  }
}
