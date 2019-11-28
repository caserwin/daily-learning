package gpx

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object GraphXFilterDemo {
  /**
    * 属性演示
    */
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

  def main(args: Array[String]) {
    val bsg = new BaseSparkGraph("demo1")

    implicit val spark: SparkSession = bsg.spark
    val graph = bsg.getGraph

    println("找出图中年龄大于30的顶点：")
    graph.vertices.filter { case (_, (_, age)) => age > 30 }.foreach {
      case (_, (name, age)) => println(s"$name is $age")
    }

    println("找出图中属性大于5的边：")
    graph.edges.filter(e => e.attr > 5).foreach(e => println(s"${e.srcId} to ${e.dstId} att ${e.attr}"))

    //triplets操作，((srcId, srcAttr), (dstId, dstAttr), attr)
    println("列出边属性>5的tripltes：")
    for (triplet <- graph.triplets.filter(t => t.attr > 5)) {
      println(s"${triplet.srcAttr._1}-${triplet.srcAttr._2} likes ${triplet.dstAttr._1}-${triplet.dstAttr._2} ")
    }

    spark.stop()
  }
}
