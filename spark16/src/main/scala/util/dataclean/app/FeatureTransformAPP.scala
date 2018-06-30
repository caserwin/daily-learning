package util.dataclean.app

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import util.dataclean.service.FeatureCleanService

/**
  * Created by yidxue on 2018/6/26
  */
object FeatureTransformAPP {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark Hive Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    //    val hqlContext = new org.apache.spark.sql.hive.HiveContext(sc)
    //
    //    // trainPerson && testPerson
    //    val tables = args(0)
    //    val trainDF = DBService.getData(hqlContext, tables)

    val dataSeq = Seq(
      ("11", "Ezra", "male", 33, 52, 173, "true", 20000, "bad", "0", "2018-06-18"),
      ("12", "Daisy", "female", 34, 59, 169, "false", 14500, "ordinary", "0", "2018-06-18"),
      ("14", "Poppy", "other", 66, 55, 155, "false", 16000, "ordinary", "0", "2018-06-18"),
      ("15", "Hazel", "female", 18, 80, 155, "false", 0, "bad", "1", "2018-06-18")
    )
    val trainDF = sc.parallelize(dataSeq).toDF("cid", "name", "gender", "age", "weight", "height", "hashouse", "income", "credit", "label", "l_date")

    //    trainDF.printSchema()
    FeatureCleanService.clean(trainDF).show()
  }
}
