package gpx

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object GraphXMapDemo {
  /**
    * 转换操作
    */
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

  def main(args: Array[String]) {
    val bsg = new BaseSparkGraph("demo2")

    implicit val spark: SparkSession = bsg.spark
    val graph = bsg.getGraph

    println("顶点的转换操作，顶点age + 10：")
    graph.mapVertices { case (id, (name, age)) => (id, (name, age + 10)) }.vertices.foreach(v => println(s"${v._2._1} is ${v._2._2}"))

    println("边的转换操作，边的属性 * 2：")
    graph.mapEdges(e => e.attr * 2).edges.foreach(e => println(s"${e.srcId} to ${e.dstId} att ${e.attr}"))

    graph.mapTriplets(x=> x.attr)
    spark.stop()
  }
}
