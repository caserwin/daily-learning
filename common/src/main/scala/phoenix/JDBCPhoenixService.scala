package phoenix

import java.sql.{Connection, DriverManager}
import phoenix.bean.RowsBean
import scala.collection.mutable

/**
  * 这部分代码参考资料如下：
  *             1. 批量插入(重要)：http://viralpatel.net/blogs/batch-insert-in-java-jdbc/
  *             2. 查询(重要)：https://alvinalexander.com/java/edu/pj/jdbc/jdbc0003
  *             3. https://stackoverflow.com/questions/37995067/jdbc-delete-insert-using-batch
  *             4. https://stackoverflow.com/questions/3784197/efficient-way-to-do-batch-inserts-with-jdbc
  *             5. why setAutoCommit(false) : https://stackoverflow.com/questions/32739719/what-happens-on-connection-setautocommit-false
  */
object JDBCPhoenixService {

  /**
    * 建表
    */
  def createPhoenixTable(tableName: String, fields: Seq[String], conn: Connection, buckets: Long = 20, TTL: Long = 31536000): Int = {
    val sql = "CREATE TABLE IF NOT EXISTS " + tableName + s"(${fields.head} VARCHAR PRIMARY KEY," + fields.drop(1).mkString("INFO.", " VARCHAR, INFO.", " VARCHAR)") + " SALT_BUCKETS=" + buckets + "," + " TTL=" + TTL
    println(sql)
    conn.createStatement.executeUpdate(sql)
  }

  /**
    * 查询数据
    */
  def selectPhoenix(conn: Connection, sql: String): mutable.MutableList[RowsBean] = {
    val mls = mutable.MutableList[RowsBean]()
    val stmt = conn.createStatement()
    val results = stmt.executeQuery(sql)
    while (results.next()) {
      val rowb = new RowsBean
      rowb.id = results.getString("ID")
      rowb.name = results.getString("NAME")
      rowb.city = results.getString("CITY")
      rowb.discount = results.getString("NUM")
      mls += rowb
    }
    stmt.close()
    mls
  }

  /**
    * 单条记录的增删改
    */
  def upsertPhoenix(conn: Connection, sql: String): Int = {
    val num = conn.createStatement.executeUpdate(sql)
    conn.commit()
    num
  }

  /**
    * 批处理：批量插入
    */
  def insertBatchPhoenix(conn: Connection, table: String, BeanLS: Seq[RowsBean], batchSize: Int): Unit = {
    conn.setAutoCommit(false)
    val preStmt = conn.prepareStatement(s"UPSERT INTO $table (ID, NAME, CITY, NUM) VALUES (?, ?, ?, ?)")
    var count = 0
    BeanLS.foreach(
      rowBean => {
        preStmt.setString(1, rowBean.id)
        preStmt.setString(2, rowBean.name)
        preStmt.setString(3, rowBean.city)
        preStmt.setString(4, rowBean.discount)
        // 把当前sql添加到批命令中。
        preStmt.addBatch()
        count = count + 1
        if (count % batchSize == 0) {
          preStmt.executeBatch()
        }
      }
    )
    preStmt.executeBatch()
    conn.commit()
    conn.setAutoCommit(true)
    preStmt.close()
  }

  /**
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
    conn.setAutoCommit(true)
    stmt.close()
  }
}
