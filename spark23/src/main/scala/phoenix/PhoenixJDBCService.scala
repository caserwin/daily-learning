package phoenix

import java.sql.{Connection, DriverManager}
import scala.collection.mutable

/**
  * User: Erwin
  * Date: 17/12/27 下午2:29
  * Description: 
  */
object PhoenixJDBCService {

  /**
    * 建表
    */
  def createPhoenixTable(tableName: String, fields: Seq[String], conn: Connection, buckets: Long = 50, TTL: Long = 31536000): Int = {
    val tableNameDecorator = if (tableName.contains("-")) "\"" + tableName + "\"" else tableName
    conn.createStatement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableNameDecorator
      + s"(${fields.head} VARCHAR PRIMARY KEY,"
      + fields.drop(1).mkString("INFO.", " VARCHAR, INFO.", " VARCHAR)")
      + " SALT_BUCKETS=" + buckets + ","
      + " TTL=" + TTL
    )
  }

  def selectPhoenix(zkAddr: String, sql: String): mutable.MutableList[RowsBean] = {
    val mls = mutable.MutableList[RowsBean]()
    val conn = DriverManager.getConnection(s"jdbc:phoenix:$zkAddr")
    val statement = conn.prepareStatement(sql)
    val results = statement.executeQuery()
    while (results.next()) {
      val rowb = new RowsBean
      rowb.col1 = results.getString("col1")
      rowb.col2 = results.getString("col2")
      rowb.col3 = results.getString("col3")
      rowb.col4 = results.getString("col4")
      mls.:+(rowb)
    }
    statement.close()
    conn.close()
    mls
  }

  def upsertPhoenix(zkAddr: String, sql: String): Int = {
    val conn = DriverManager.getConnection(s"jdbc:phoenix:$zkAddr")
    val num = conn.createStatement.executeUpdate(sql)
    conn.commit()
    conn.close()
    num
  }
}
