package performace

import org.apache.spark.{SparkConf, SparkContext}

/**
  * User: Erwin
  * Date: 17/11/15 上午10:09
  * Description: 
  */
object SparkGroupByKey1 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val words = Array("one", "two", "two", "three", "three", "three")
    val wordPairsRDD = sc.parallelize(words).map(word => (word, 1))
    //    val wordCountsWithGroup = wordPairsRDD.groupByKey().map(t => (t._1, t._2.sum)).collect()

    val wordCountsWithGroup = wordPairsRDD.groupByKey().map(
      t => {
        val word = t._1
        val numLs = t._2.toList
        println(numLs)
        (word, numLs.min)
      }
    ).collect()

    wordCountsWithGroup.foreach(println(_))
  }
}
