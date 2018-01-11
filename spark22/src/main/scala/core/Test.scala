package core

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._
import org.apache.spark.{SparkConf, SparkContext}

object Test {

  case class EventlogJMF(refnum4: String, eventtype: String, timestamp: String, webservername: String)


  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("Scala UDAF Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._
//    val seqT = Seq(
//      (1, Map("John" -> 12.5, "Alice" -> 5.5)),
//      (1, Map("Jim" -> 16.5, "Alice" -> 4.0)),
//      (2, Map("John" -> 13.5, "Jim" -> 2.5))
//    )
//    val input = sc.parallelize(seqT).toDF("id", "scoreMap")
//    input.show(truncate = false)


//    val output = Seq("aaaaa", "bbbb")
////    sc.makeRDD(output).saveAsTextFile("output")
//    println(sc.makeRDD(output).count())
//
//
    val dataSeq = Seq(
      (1, "zhangsan", "hangzhou"),
      (1, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val input = sc.parallelize(dataSeq).toDF("id", "name", "city")

    input.groupBy($"id").agg(
      min($"name")
    ).show()

//    val filterLS = List("1", "2", "17", "16")
//
//    val aaDF = input.filter($"id".isin(filterLS:_*))
//    aaDF.show(truncate = false)


//    val output=  Set("ref4","et","timestamp","webservername")
//    println(output.map{re => re+"1"}.mkString(","))
//
//    println(output.contains("et"))


//    val aa =1000
//    println(aa == 1000.0)
  }
}
