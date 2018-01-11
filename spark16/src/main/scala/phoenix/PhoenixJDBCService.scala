package phoenix

import java.sql.Connection
import phoenix.bean.RowsBean
import scala.collection.mutable

/**
  *
  * 这部分代码参考资料如下：
  *             1. http://viralpatel.net/blogs/batch-insert-in-java-jdbc/
  *             2. https://stackoverflow.com/questions/37995067/jdbc-delete-insert-using-batch
  *             3. https://stackoverflow.com/questions/3784197/efficient-way-to-do-batch-inserts-with-jdbc
  *             4. why setAutoCommit(false) : https://stackoverflow.com/questions/32739719/what-happens-on-connection-setautocommit-false
  *
  */
object PhoenixJDBCService {

  def createPhoenixTable(tableName: String, fields: Seq[String], conn: Connection, buckets: Long = 50, TTL: Long = 31536000): Int = {
    val tableNameDecorator = if (tableName.contains("-")) "\"" + tableName + "\"" else tableName
    conn.createStatement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableNameDecorator
      + s"(${fields.head} VARCHAR PRIMARY KEY,"
      + fields.drop(1).mkString("INFO.", " VARCHAR, INFO.", " VARCHAR)")
      + " SALT_BUCKETS=" + buckets + ","
      + " TTL=" + TTL
    )
  }

  /**
    * 查询数据
    */
  def selectPhoenix(conn: Connection, sql: String): mutable.MutableList[RowsBean] = {
    val mls = mutable.MutableList[RowsBean]()
    val stmt = conn.prepareStatement(sql)
    val results = stmt.executeQuery()
    while (results.next()) {
      val rowb = new RowsBean
      rowb.rowkey = results.getString("ROWKEY")
      rowb.name = results.getString("NAME")
      rowb.city = results.getString("CITY")
      rowb.discount = results.getString("DISCOUNT")
      mls.:+(rowb)
    }
    stmt.close()
    conn.close()
    mls
  }

  /**
    * 单条记录的增删改
    */
  def upsertPhoenix(conn: Connection, sql: String): Int = {
    val num = conn.createStatement.executeUpdate(sql)
    conn.commit()
    conn.close()
    num
  }

  /**
    * 批处理：批量插入
    */
  def insertBatchPhoenix(conn: Connection, BeanLS: Seq[RowsBean], batchSize: Int): Unit = {
    conn.setAutoCommit(false)
    val preStmt = conn.prepareStatement("INSERT INTO ROWKEYTEST (ROWKEY, NAME, CITY, DISCOUNT) VALUES (?, ?, ?, ?)")
    var count = 0

    BeanLS.foreach(
      rowBean => {
        preStmt.setString(1, rowBean.rowkey)
        preStmt.setString(2, rowBean.name)
        preStmt.setString(3, rowBean.city)
        preStmt.setString(4, rowBean.discount)
        count = count + 1
        if (count % batchSize == 0) {
          preStmt.addBatch()
        }
      }
    )

    preStmt.executeBatch()
    conn.commit()
    // 参考资料1, 这里貌似不需要 conn.commit()
    preStmt.close()
    conn.close()
  }

  /**
    *
    * 批处理：这方法参考资料2，资料3. 可发现用于批量删除或者插入。
    */
  def insertOrDeleteBatchPhoenix(conn: Connection, sqlLS: Seq[String], batchSize: Int): Unit = {
    conn.setAutoCommit(false)
    val stmt = conn.createStatement()
    var count = 0

    sqlLS.foreach(
      sql => {
        stmt.addBatch(sql)
        count = count + 1
        if (count % batchSize == 0) {
          stmt.executeBatch()
        }
      }
    )

    stmt.executeBatch()
    conn.commit()
    // 参考资料1, 这里貌似不需要 conn.commit()
    stmt.close()
    conn.close()
  }
}
