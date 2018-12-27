package sql

import org.apache.spark.sql.UserDefinedFunction
import org.apache.spark.sql.functions.udf
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
import sql.udaf.MergeListsUDAF

/**
  * Created by yidxue on 2018/12/27
  */
object SparkSQLGroupByDemo2 {

  val flatten: UserDefinedFunction = udf((xs: Seq[Seq[String]]) => xs.flatten)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new HiveContext(sc)
    import sqlContext.implicits._

    val dataSeq = Seq(
      ("1", "lisi", "12", Seq(1, 2, 3, 4)),
      ("1", "lisi", "3", Seq(4, 5, 6)),
      ("1", "wangwu", "3", Seq(6, 7, 8)),
      ("2", "wangwu", "4", Seq(1, 3, 4))
    )
    val inputDF = sc.parallelize(dataSeq).toDF("id", "name", "num", "array")
    inputDF.printSchema()

    val mergeUDAF = new MergeListsUDAF()
    inputDF.groupBy("id").agg(mergeUDAF($"array")).show(truncate = false)
    sc.stop()
  }
}
