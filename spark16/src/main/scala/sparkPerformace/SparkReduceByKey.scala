package sparkPerformace

import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * User: Erwin
  * Date: 17/11/15 上午10:09
  * Description:
  */
object SparkReduceByKey {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._
    //    val words = Array("one", "two", "two", "three", "three", "three")
    //    val wordPairsRDD = sc.parallelize(words).map(word => (word, 1))
    //    val wordCountsWithReduce = wordPairsRDD.reduceByKey(_ + _).collect()
    //
    //    wordCountsWithReduce.foreach(println(_))

    val dataSeq1 = Seq(
      ("g1", "u1", "zhangsan", "hangzhou"),
      ("g2", "u2", "lisi", "beijing"),
      ("g3", "u3", "wangwu", "shanghai"),
      ("g3", "u3", "wangwu", "shanghai"),
      ("g3", "u3", "wangwu", "shanghai"),
      ("g2", "u2", "zhejiang", "wehzhou"),
      ("g1", "u1", "beijing", "beijing")
    )
    val input = sc.parallelize(dataSeq1).toDF("gid", "uid", "name", "city")


    val crdd = input.rdd.map {
      case Row(gid: String, uid: String, name: String, city: String) =>
        ((gid, uid), (name, city))
    }.reduceByKey((a ,b) => (a._1+":"+b._1, a._2+":"+b._2))

    crdd.collect().foreach(println(_))
  }

  def addtup(row1: (String, String), row2: (String, String)): (String, String) = {
    val (a1, a2) = (row1._1, row1._2)
    val (b1, b2) = (row2._1, row2._2)
    (a1 + ":" + b1, a2 + ":" + b2)
  }
}
