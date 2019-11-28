package gpx

import org.apache.log4j.{Level, Logger}
import org.apache.spark.graphx._
import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2019-11-14
  */
object GraphXJoinDemo {
  /**
    * 连接操作
    */
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

  def main(args: Array[String]): Unit = {
    val bsg = new BaseSparkGraph("demo4")

    implicit val spark: SparkSession = bsg.spark
    val graph = bsg.getGraph

    case class User(name: String, age: Int, inDeg: Int, outDeg: Int)
    //创建一个新图，顶点VD的数据类型为User，并从graph做类型转换
    val initialUserGraph: Graph[User, Int] = graph.mapVertices { case (id, (name, age)) => User(name, age, 0, 0) }

    //initialUserGraph与inDegrees、outDegrees（RDD）进行连接，并修改initialUserGraph中inDeg值、outDeg值
    val userGraph = initialUserGraph
      .outerJoinVertices(initialUserGraph.inDegrees) {
        case (id, u, inDegOpt) => User(u.name, u.age, inDegOpt.getOrElse(0), u.outDeg)
      }
      .outerJoinVertices(initialUserGraph.outDegrees) {
        case (id, u, outDegOpt) => User(u.name, u.age, u.inDeg, outDegOpt.getOrElse(0))
      }

    println("连接图的属性：")
    userGraph.vertices.foreach(v => println(s"${v._2.name} inDeg: ${v._2.inDeg}  outDeg: ${v._2.outDeg}"))

    println("出度和入读相同的人员：")
    userGraph.vertices.filter { case (id, u) => u.inDeg == u.outDeg }.foreach { case (id, property) => println(property.name) }

    spark.stop()
  }
}
