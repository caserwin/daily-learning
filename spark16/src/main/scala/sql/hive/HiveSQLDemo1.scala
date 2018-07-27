package sql.hive

import java.text.SimpleDateFormat
import java.util.Date
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/1/28
  */
object HiveSQLDemo1 {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("Spark Hive Example").setMaster("local[*]")
    // TODO modify here
    //    conf.set("spark.executor.extraClassPat","")
    val sc = new SparkContext(conf)
    val hqlContext = new org.apache.spark.sql.hive.HiveContext(sc)
    import hqlContext.implicits._

    val curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date)
    val fields = Seq("id", "name", "city")

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "hangzhou"),
      (3, "wangwu", "shanghai"),
      (4, "zhaoliu", "shanghai")
    )
    val inputDF = sc.parallelize(dataSeq1).toDF(fields: _*)

    HiveUtil.createHiveTable(hqlContext, "testtable1", fields)
    HiveUtil.insertHiveTable(hqlContext, "testtable1", curDate, inputDF, fields)
  }
}
