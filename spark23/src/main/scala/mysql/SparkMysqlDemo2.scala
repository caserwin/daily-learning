package mysql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/**
  * Created by yidxue on 2021/8/29
  */
object SparkMysqlDemo2 {

  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = SparkSession.builder().appName("FillFatigue2Adb").getOrCreate()
    val jdbcDriver = "com.mysql.jdbc.Driver"
    val jdbcUrl = "jdbc:mysql://localhost:3306?rewriteBatchedStatements=true"
    val tableName = "test.tb_name"
    val jdbcUser = "userName"
    val jdbcPassword = "passWord"
    val ds = args(0)

    import spark.implicits._

    //读填充疲劳度之后的ADB表
    val AdbDataAfterFill = spark.read.format("jdbc")
      .option("driver", jdbcDriver)
      .option("url", jdbcUrl)
      .option("dbtable", s""" (select * from $tableName where ds = '$ds') as newTableName """)
      .option("user", jdbcUser)
      .option("password", jdbcPassword)
      .option("fetchsize", 1000)
      .option("partitionColumn", "send_hour")
      .option("lowerBound", 0)
      .option("upperBound", 24)
      .option("numPartitions", "30")
      .load()
      .groupBy("user_id", "bucket")
      .agg(
        count(lit(1)).alias("cur_send_cnt"),
        concat_ws(",", collect_list($"send_hour")).alias("send_hours")
      )

    AdbDataAfterFill.filter($"user_id" === "yd.eda4d7f8b3364c1c9@163.com").show(1000, truncate = false)
  }
}
