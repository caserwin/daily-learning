package gpx

import org.apache.spark.graphx.{Edge, Graph}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

class BaseSparkGraph(appName: String) {

  //设置运行环境
  val spark: SparkSession = SparkSession.builder().appName(appName).master("local[*]").getOrCreate()

  def getGraph: Graph[(String, Int), Int] = {
    //顶点的数据类型是VD:(String,Int)
    val vertexArray = Array(
      (1L, ("Alice", 28)),
      (2L, ("Bob", 27)),
      (3L, ("Charlie", 65)),
      (4L, ("David", 42)),
      (5L, ("Ed", 55)),
      (6L, ("Fran", 50))
    )

    //边的数据类型ED:Int
    val edgeArray = Array(
      Edge(2L, 1L, 7),
      Edge(2L, 4L, 2),
      Edge(3L, 2L, 4),
      Edge(3L, 6L, 3),
      Edge(4L, 1L, 1),
      Edge(5L, 2L, 2),
      Edge(5L, 3L, 8),
      Edge(5L, 6L, 3)
    )

    //构造vertexRDD和edgeRDD
    val vertexRDD: RDD[(Long, (String, Int))] = spark.sparkContext.parallelize(vertexArray)
    val edgeRDD: RDD[Edge[Int]] = spark.sparkContext.parallelize(edgeArray)

    //构造图Graph[VD,ED]
    Graph(vertexRDD, edgeRDD)
  }
}
