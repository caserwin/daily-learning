package phoenix

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import phoenix.bean.RowsBean
import phoenix.conn.PhoenixConn
import scala.collection.mutable

/**
  * User: Erwin
  * Date: 17/12/28 上午10:05
  * Description: 
  */
object RowKeyByJDBCTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RowKey Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    // 数据源
    val tableName = "ROWKEYTEST_JDBC"
    val fields = Seq("ROWKEY", "NAME", "CITY", "NUM")
    val zkAddr = "10.29.42.42:2181"

    // 获取连接
    val conn = new PhoenixConn(zkAddr).getConn

    // 创建phoenix表
    PhoenixJDBCService.createPhoenixTable(tableName, fields, conn)

    // 写入phoenix表
    PhoenixJDBCService.upsertPhoenix(conn, s"upsert into $tableName values ('2017-12-01_id1234','lisa','hangzhou','0.75')")

    // 批量插入
    val mySeq = Seq(
      new RowsBean("2017-12-01_id1235", "lisi", "beijing", "0.8"),
      new RowsBean("2017-12-01_id1236", "wangwu", "beijing", "0.9"),
      new RowsBean("2017-12-02_id1237", "zhaoliu", "shanghai", "0.6"),
      new RowsBean("2017-12-02_id1238", "erwin", "hangzhou", "0.5"))
    PhoenixJDBCService.insertBatchPhoenix(conn, mySeq, 3)

    // 查询phoenix表，这里不能写成 select * from ...的方式
    val mls1: mutable.MutableList[RowsBean] = PhoenixJDBCService.selectPhoenix(conn, s"select ROWKEY, NAME, CITY, NUM from $tableName")
    mls1.foreach(rowbean => println(rowbean.rowkey + "\t" + rowbean.name + "\t" + rowbean.city + "\t" + rowbean.discount))

    // 修改表中数据
    PhoenixJDBCService.upsertPhoenix(conn, s"upsert into $tableName values ('2017-12-02_id1237','yuyi','hangzhou','0.1')")

    println("======================================================")
    // 查询phoenix表
    val mls2: mutable.MutableList[RowsBean] = PhoenixJDBCService.selectPhoenix(conn, s"select ROWKEY, NAME, CITY, NUM from $tableName")
    mls2.foreach(rowbean => println(rowbean.rowkey + "\t" + rowbean.name + "\t" + rowbean.city + "\t" + rowbean.discount))

    // 删除表中数据
    PhoenixJDBCService.upsertPhoenix(conn, s"delete from $tableName where rowkey='2017-12-01_id1234'")

    println("======================================================")
    // 查询phoenix表
    val mls3: mutable.MutableList[RowsBean] = PhoenixJDBCService.selectPhoenix(conn, s"select ROWKEY, NAME, CITY, NUM from $tableName")
    mls3.foreach(rowbean => println(rowbean.rowkey + "\t" + rowbean.name + "\t" + rowbean.city + "\t" + rowbean.discount))

  }
}
