//package sql
//
//import org.apache.spark.{SparkConf, SparkContext}
//import org.apache.spark.sql.SQLContext
//
///**
//  * Created by yidxue on 2018/1/30
//  */
//object SaprkSQLExplodeDemo {
//
//  def main(args: Array[String]): Unit = {
//    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
//    val sc = new SparkContext(conf)
//    val sqlContext = new SQLContext(sc)
//    import sqlContext.implicits._
//
//    val dataSeq1 = Seq(
//      "lisi  1",
//      "lisi  1",
//      "wangwu  2",
//      "wangwu  1"
//    )
//    val inputDF = sc.parallelize(dataSeq1).toDF("context")
//    inputDF.show()
//
//    val tmpDF = inputDF.explode("context", zip("split")) { context: String => context.split("\\s+") }
//    tmpDF.show()
//
//    val resDF = tmpDF.select("context", "split")
//    resDF.show()
//  }
//}
