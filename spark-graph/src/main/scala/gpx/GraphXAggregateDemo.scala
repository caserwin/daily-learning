package gpx

import org.apache.log4j.{Level, Logger}
import org.apache.spark.graphx._
import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2019-11-14
  */
object GraphXAggregateDemo {
  /**
    * 聚合操作
    */
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

  def main(args: Array[String]): Unit = {
    val bsg = new BaseSparkGraph("demo5")

    implicit val spark: SparkSession = bsg.spark
    val graph = bsg.getGraph

    case class User(name: String, age: Int, inDeg: Int, outDeg: Int)

    //创建一个新图，顶点VD的数据类型为User，并从graph做类型转换
    val initialUserGraph: Graph[User, Int] = graph.mapVertices { case (id, (name, age)) => User(name, age, 0, 0) }
    val userGraph = initialUserGraph
      .outerJoinVertices(initialUserGraph.inDegrees) {
        case (id, u, inDegOpt) => User(u.name, u.age, inDegOpt.getOrElse(0), u.outDeg)
      }
      .outerJoinVertices(initialUserGraph.outDegrees) {
        case (id, u, outDegOpt) => User(u.name, u.age, u.inDeg, outDegOpt.getOrElse(0))
      }

    val oldestFollower: VertexRDD[(String, Int)] = userGraph.aggregateMessages[(String, Int)](
      // 将源顶点的属性发送给目标顶点，map过程
      edge => Iterator((edge.dstId, (edge.srcAttr.name, edge.srcAttr.age))),
      // 得到最大追求者，reduce过程
      (a, b) => if (a._2 > b._2) a else b
    )

    userGraph.vertices.leftJoin(oldestFollower) {
      (id, user, optOldestFollower) =>
        optOldestFollower match {
          case None => s"${user.name} does not have any followers."
          case Some((name, age)) => s"$name is the oldest follower of ${user.name}."
        }
    }.foreach { case (id, str) => println(str) }

    spark.stop()
  }
}
