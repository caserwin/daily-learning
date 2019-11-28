package gpx

import org.apache.log4j.{Level, Logger}
import org.apache.spark.graphx.VertexId
import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2019-11-14
  */
object GraphXDegreeDemo {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

  def main(args: Array[String]): Unit = {
    val bsg = new BaseSparkGraph("demo1")

    implicit val spark: SparkSession = bsg.spark
    val graph = bsg.getGraph

    println("每个节点的入度")
    graph.inDegrees.foreach(x => println(x))

    println("每个节点的出度")
    graph.outDegrees.foreach(x => println(x))

    println("每个节点的度数")
    graph.degrees.foreach(x => println(x))

    println("找出图中最大的出度、入度、度数：")

    def max(a: (VertexId, Int), b: (VertexId, Int)): (VertexId, Int) = {
      if (a._2 > b._2) a else b
    }

    println("max of outDegrees:" + graph.outDegrees.reduce(max) + " max of inDegrees:" + graph.inDegrees.reduce(max) + " max of Degrees:" + graph.degrees.reduce(max))
    spark.stop()
  }
}
