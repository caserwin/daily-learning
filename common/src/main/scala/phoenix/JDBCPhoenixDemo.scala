package phoenix

import phoenix.bean.RowsBean
import phoenix.conn.PhoenixConn
import scala.collection.mutable

/**
  * User: Erwin
  * Date: 17/12/28 上午10:05
  * Description: 
  */
object JDBCPhoenixDemo {

  def main(args: Array[String]): Unit = {
    // 数据源
    val tableName = args(0)
    val fields = Seq("ID", "NAME", "CITY", "NUM")
    val zkAddr = "localhost:2181"

    // 获取连接
    val conn = new PhoenixConn(zkAddr).getConn

    // 创建phoenix表
    JDBCPhoenixService.createPhoenixTable(tableName, fields, conn)

    // 插入
    JDBCPhoenixService.upsertPhoenix(conn, s"upsert into $tableName values ('id1234','lisa','hangzhou','0.75')")
    // 查询，这里不能写成 select * from ...的方式
    val mls1: mutable.MutableList[RowsBean] = JDBCPhoenixService.selectPhoenix(conn, s"select ID, NAME, CITY, NUM from $tableName")
    mls1.foreach(rowbean => println(rowbean.id + "\t" + rowbean.name + "\t" + rowbean.city + "\t" + rowbean.discount))
    println("======================================================")

    // 批量插入
    val mySeq = Seq(
      new RowsBean("id1235", "lisi", "beijing", "0.8"),
      new RowsBean("id1236", "wangwu", "beijing", "0.9"),
      new RowsBean("id2237", "zhaoliu", "shanghai", "0.6"),
      new RowsBean("id2238", "erwin", "hangzhou", "0.5"))
    JDBCPhoenixService.insertBatchPhoenix(conn, tableName, mySeq, 10)
    // 查询
    val mls2: mutable.MutableList[RowsBean] = JDBCPhoenixService.selectPhoenix(conn, s"select ID, NAME, CITY, NUM from $tableName")
    mls2.foreach(rowbean => println(rowbean.id + "\t" + rowbean.name + "\t" + rowbean.city + "\t" + rowbean.discount))
    println("======================================================")

    // 修改表中数据，必须指定主键，然后覆盖该主键的其他字段
    JDBCPhoenixService.upsertPhoenix(conn, s"upsert into $tableName values ('id2237','yuyi','hangzhou','0.1')")
    // 查询
    val mls3: mutable.MutableList[RowsBean] = JDBCPhoenixService.selectPhoenix(conn, s"select ID, NAME, CITY, NUM from $tableName")
    mls3.foreach(rowbean => println(rowbean.id + "\t" + rowbean.name + "\t" + rowbean.city + "\t" + rowbean.discount))
    println("======================================================")

    // 删除表中数据
    JDBCPhoenixService.upsertPhoenix(conn, s"delete from $tableName where id='id1234'")
    // 查询phoenix表
    val mls4: mutable.MutableList[RowsBean] = JDBCPhoenixService.selectPhoenix(conn, s"select ID, NAME, CITY, NUM from $tableName")
    mls4.foreach(rowbean => println(rowbean.id + "\t" + rowbean.name + "\t" + rowbean.city + "\t" + rowbean.discount))
    println("======================================================")
  }
}
