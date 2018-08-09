package phoenix.conn

import java.sql.{Connection, DriverManager, SQLException}
import java.util.Properties

/**
  * Warning: This Class is not thread safe.
  */
class PhoenixConn(var zkAddr: String) {

  private var conn: Connection = _

  /**
    * Hbase参数配置可参考：https://hbase.apache.org/book.html#config.files
    * 常用参数优化：http://blog.csdn.net/jiangshouzhuang/article/details/52355670
    */
  def getConn: Connection = {
    if (this.conn == null) {
      try
        Class.forName("org.apache.phoenix.jdbc.PhoenixDriver")
      catch {
        case e: ClassNotFoundException => e.printStackTrace()
      }

      try {
        val props = new Properties
        props.setProperty("hbase.rpc.timeout", "60000")
        props.setProperty("hbase.client.operation.timeout", "60000")
        props.setProperty("hbase.client.scanner.timeout.period", "60000")
        val conn = DriverManager.getConnection("jdbc:phoenix:" + this.zkAddr, props)
        this.conn = conn
      } catch {
        case e: SQLException => e.printStackTrace()
      }
    }
    this.conn
  }
}
