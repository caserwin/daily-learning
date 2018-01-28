package performace

import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

/**
  * User: Erwin
  * Date: 17/11/15 上午10:10
  * Description: 
  */
object SparkAggregateByKey {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

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
    }.aggregateByKey(ArrayBuffer[(String, String)]())((ls,v) => ls+=v, (ls1,ls2) => ls1++=ls2).mapValues(_.toList)

    crdd.foreach(println(_))
  }
}
