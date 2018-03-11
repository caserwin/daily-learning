package csv

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, SQLContext, SaveMode}

/**
  * Created by yidxue on 2018/3/11
  */
object SparkCSVUtil {

  def saveDFToCsv(df: DataFrame, hdfsPath: String): Unit = {
    df
      .write
      .mode(SaveMode.Overwrite)
      .option("delimiter", "\t")
      .option("nullValue", "")
      .option("header", "true")
      .format("com.databricks.spark.csv")
      .save(hdfsPath)
  }

  def getDFFromCSV(sqlContext: SQLContext, path: String): DataFrame = {
    sqlContext.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("delimiter", "\t")
      .load(path)
  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Get Data Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    getDFFromCSV(sqlContext, "/Users/cisco/data/jmt explanation/2018-03-03.csv").show(3)

    /**
      * +-----------------+----------+---------+--------+-------+---------+-----------+--------------+----------+
      * |           CONFID|       GID|     UID_|USERTYPE|     OS|OSVERSION|    BROWSER|BROWSERVERSION|   JMETHOD|
      * +-----------------+----------+---------+--------+-------+---------+-----------+--------------+----------+
      * |12345678123456789|  12345678|        0|  UPDATE|Windows|      7.0|         IE|           7.0|   ActiveX|
      * |12345678123456789|         0| 12345678|  UPDATE|Windows|     10.0|         IE|           7.0|   ActiveX|
      * |12345678123456789|  12345678|        0|  RETURN|    IOS|      7.0|      Other|         Other|   ActiveX|
      * +-----------------+----------+---------+--------+-------+---------+-----------+--------------+----------+
      */
  }
}

