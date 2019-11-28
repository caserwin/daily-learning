package gpx

import org.apache.log4j.{Level, Logger}
import org.apache.spark.graphx._
import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2019-11-14
  */
object GraphXMinNodeDistDemo {
  /**
    * 找出节点5到各点的最短距离
    */
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

  def main(args: Array[String]): Unit = {
    val bsg = new BaseSparkGraph("demo6")

    implicit val spark: SparkSession = bsg.spark
    val graph = bsg.getGraph

    println("找出5到各顶点的最短：")

    // 定义源点
    val sourceId: VertexId = 5L
    val initialGraph = graph.mapVertices((id, _) => if (id == sourceId) 0.0 else Double.PositiveInfinity)

    val sssp = initialGraph.pregel(Double.PositiveInfinity)(
      (id, dist, newDist) => math.min(dist, newDist),
      triplet => {
        if (triplet.srcAttr + triplet.attr < triplet.dstAttr) {
          Iterator((triplet.dstId, triplet.srcAttr + triplet.attr))
        } else {
          Iterator.empty
        }
      },
      // 最短距离
      (a, b) => math.min(a, b)
    )
    println(sssp.vertices.collect().mkString("\n"))

    spark.stop()
  }
}
