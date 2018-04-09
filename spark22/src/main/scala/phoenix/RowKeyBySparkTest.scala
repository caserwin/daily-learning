package phoenix

import org.apache.phoenix.spark._
import org.apache.spark.sql.{SaveMode, SparkSession}
import phoenix.conn.PhoenixConn

object RowKeyBySparkTest {
  def main(args: Array[String]): Unit = {

    // 数据源
    val tableName = "ROWKEYTEST_JDBC_SPARK22"
    val fields = Seq("ROWKEY", "NAME", "CITY")
    val zkAddr = "rphf1kaf001.qa.webex.com:2181"

    // 获取连接
    val conn = new PhoenixConn(zkAddr).getConn

    // 创建phoenix表
    PhoenixJDBCService.createPhoenixTable(tableName, fields, conn)

    val spark = SparkSession.builder().appName("Spark SQL basic example").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    // 数据源
    val dataSeq1 = Seq(
      ("a1dsc", "zhangsan", "hangzhou"),
      ("a2dsd", "lisi", "beijing"),
      ("a3ace", "wangwu", "shanghai")
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq1).toDF("ROWKEY", "NAME", "CITY")

    // 写入phoenix表
//    inputDF.write.format("org.apache.phoenix.spark").mode(SaveMode.Overwrite).options(Map("table" -> tableName, "zkUrl" -> zkAddr)).save()
    inputDF.saveToPhoenix(tableName, zkUrl = Some(zkAddr))

    // 查询phoenix表。
    val df = new SparkSqlContextFunctions(spark.sqlContext).phoenixTableAsDataFrame(tableName, columns = fields, zkUrl = Some(zkAddr))
    df.show()

    // 删除表中数据

    // 修改表中数据

    // 删除表

  }
}
