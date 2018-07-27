package sql.hive

import java.text.SimpleDateFormat
import java.util.Date
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/7/27
  */
object HiveSQLDemo2 {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("Spark Hive Example").setMaster("local[*]")
    // TODO modify here
    //    conf.set("spark.executor.extraClassPat","")
    val sc = new SparkContext(conf)
    val hqlContext = new org.apache.spark.sql.hive.HiveContext(sc)
    hqlContext.setConf("hive.exec.dynamic.partition", "true")
    hqlContext.setConf("hive.exec.dynamic.partition.mode", "nonstrict")
    import hqlContext.implicits._
    val fields = Seq("id", "name", "city", "l_date")

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou", "2018-07-01"),
      (2, "lisi", "hangzhou", "2018-07-01"),
      (3, "wangwu", "shanghai", "2018-07-02"),
      (4, "zhaoliu", "shanghai", "2018-07-02")
    )
    val inputDF = sc.parallelize(dataSeq1).toDF(fields: _*)

    HiveUtil.createHiveTable(hqlContext, "testtable2", Seq("id", "name", "city"))
    HiveUtil.insertByDynamic(hqlContext, "testtable2", inputDF, fields)
  }
}
