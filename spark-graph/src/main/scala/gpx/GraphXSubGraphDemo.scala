package gpx

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object GraphXSubGraphDemo {
  /**
    * 结构操作
    */
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

  def main(args: Array[String]) {
    val bsg = new BaseSparkGraph("demo3")

    implicit val spark: SparkSession = bsg.spark
    val graph = bsg.getGraph

    println("顶点年纪>30的子图：")
    val subGraph = graph.subgraph(vpred = (id, vd) => vd._2 >= 30)

    println("子图所有顶点：")
    subGraph.vertices.foreach(v => println(s"${v._2._1} is ${v._2._2}"))

    println("子图所有边：")
    subGraph.edges.foreach(e => println(s"${e.srcId} to ${e.dstId} att ${e.attr}"))

    spark.stop()
  }
}
