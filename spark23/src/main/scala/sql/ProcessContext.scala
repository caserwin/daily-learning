package sql

import java.io.File
import java.util.Date
import java.text.SimpleDateFormat
import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/11/19
  */
class ProcessContext(appName: String) {

  val start = new Date()
  val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  val jobStartTime = dateFormat.format(start)
  val warehouseLocation: String = new File("/user/hive/warehouse").getAbsolutePath

  val spark: SparkSession = SparkSession
    .builder
    .appName(appName)
    .config("spark.sql.warehouse.dir", warehouseLocation)
    .master("local[*]")
    //    .enableHiveSupport()
    .getOrCreate()

  spark.sqlContext.setConf("hive.exec.dynamic.partition", "true")
  spark.sqlContext.setConf("hive.exec.dynamic.partition.mode", "nonstrict")
  spark.sqlContext.setConf("hive.exec.max.dynamic.partitions", "3600")

  def setup(day: String): Unit = {

  }

  def clean(day: String): Unit = {
    spark.stop()
  }
}
