package phoenix

import csv.SparkCSVUtil
import org.apache.phoenix.spark._
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.joda.time.format.DateTimeFormat

/**
  * Created by yidxue on 2018/3/11
  */
object GetDataAPP {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Get Data Example")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val zkAddr = "10.29.42.42:2181"
    val day = args(0)
    val afterDay = getAfterDay(day, "yyyy-MM-dd", "yyyy-MM-dd")
    val fields = "CONFID,GID,UID_,USERTYPE,USEROS,OSVERSION,USERBROWSER,BROWSERVERSION,JOINMETHOD,REFNUM3,REFNUM4,REFNUM5,REFNUM6FIRST,REFNUM6LAST,SERVICETYPE,SERVICEID,SITEID,SITEVERSION,STARTJOINTIME,USERJMT,COUNTRY,PLATFORM,SYSTEM,ISHOST".split(",")

    val df = sqlContext.phoenixTableAsDataFrame(table = "SAP_MEETINGJMT_DEMO", columns = fields, predicate = Some(s"rowkey between '$day' and '$afterDay'"), zkUrl = Some(zkAddr))
    SparkCSVUtil.saveDFToCsv(df, s"/user/yidxue/demo/$day.csv")
  }

  def getAfterDay(str: String, inFormat: String, outFormat: String): String = {
    val formatter = DateTimeFormat.forPattern(inFormat)
    val dateTime = formatter.parseDateTime(str)
    val d = dateTime.plusDays(1)
    DateTimeFormat.forPattern(outFormat).print(d)
  }
}
