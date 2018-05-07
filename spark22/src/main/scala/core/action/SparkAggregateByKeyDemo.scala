package util.action

import org.apache.spark.sql.{Row, SparkSession}
import scala.collection.mutable.ArrayBuffer

/**
  * Created by yidxue on 2018/4/12
  */
object SparkAggregateByKeyDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      ("g1", "u1", "zhangsan", "hangzhou"),
      ("g2", "u2", "lisi", "beijing"),
      ("g3", "u3", "wangwu", "shanghai"),
      ("g3", "u3", "wangwu", "shanghai"),
      ("g3", "u3", "wangwu", "shanghai"),
      ("g2", "u2", "zhejiang", "wehzhou"),
      ("g1", "u1", "beijing", "beijing")
    )
    val input = spark.sparkContext.parallelize(dataSeq1).toDF("gid", "uid", "name", "city")

    val rdd = input.rdd.map {
      case Row(gid: String, uid: String, name: String, city: String) =>
        ((gid, uid), (name, city))
    }.aggregateByKey(ArrayBuffer[(String, String)]())((ls, v) => ls += v, (ls1, ls2) => ls1 ++= ls2).mapValues(_.toList)

    rdd.foreach(println(_))
  }
}

