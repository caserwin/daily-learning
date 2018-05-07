package phoenix

import org.apache.spark.sql.SparkSession
import phoenix.conn.PhoenixConn

/**
  * User: Erwin
  * Date: 17/12/28 上午10:04
  * Description:
  */
object RowKeyByJDBCTest {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Spark SQL basic example").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    // 数据源
    val tableName = "ROWKEYTEST_JDBC_SPARK22"
    val fields = Seq("ROWKEY", "NAME", "CITY")
    val zkAddr = "rphf1kaf001.qa.webex.com:2181"

    // 获取连接
    val conn = new PhoenixConn(zkAddr).getConn

    // 创建phoenix表
    PhoenixJDBCService.createPhoenixTable(tableName, fields, conn)

    // 数据源
    val dataSeq1 = Seq(
      ("a1dsc", "zhangsan", "hangzhou"),
      ("a2dsd", "lisi", "beijing"),
      ("a3ace", "wangwu", "shanghai")
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq1).toDF("ROWKEY", "NAME", "CITY")

    // 写入phoenix表
//    PhoenixJDBCService.upsertPhoenix(conn, s"upsert into $tableName values ('2017-12-01_id1234','lisa','hangzhou','0.75')")

    // 查询phoenix表

    // 删除表中数据

    // 修改表中数据

    // 删除表
  }
}
