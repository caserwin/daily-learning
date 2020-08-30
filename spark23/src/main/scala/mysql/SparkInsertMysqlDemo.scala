package mysql


import org.apache.spark.sql.SparkSession


/**
  * Created by yidxue on 2020-08-30
  */

object SparkInsertMysqlDemo {

  def main(args: Array[String]): Unit = {

    implicit val spark: SparkSession = SparkSession.builder().appName("").getOrCreate()
    val day: String = args(0)
    val buckets: String = args(1).split(",").map(x => "'" + x + "'").mkString(",")
    val targetDs = if (args.length > 2) args(2) else day

    val jdbcDriver = "com.mysql.jdbc.Driver"
    val jdbcUrl = "jdbc:mysql://localhost:3306?rewriteBatchedStatements=true"
    val jdbcDbtable = "my.test"
    val jdbcUser = "un"
    val jdbcPassword = "pw"


    //读 当前ADB 表 取待投放的push数据
    val df = spark.read.format("jdbc")
      .option("driver", jdbcDriver)
      .option("url", jdbcUrl)
      .option("dbtable", s""" (select * from table where ds = '20200820') as newTableName """)
      .option("user", jdbcUser)
      .option("password", jdbcPassword)
      .option("fetchsize", 1000)
      .option("partitionColumn", "send_hour")
      .option("lowerBound", 0)
      .option("upperBound", 24)
      .option("numPartitions", "30")
      .load()


    // 写入 mysql 表
    df.write
      .mode("append")
      .format("jdbc")
      .option("url", jdbcUrl)
      .option("dbtable", jdbcDbtable)
      .option("user", jdbcUser)
      .option("password", jdbcPassword)
      .option("batchsize", 10000)
      .save()
  }
}

