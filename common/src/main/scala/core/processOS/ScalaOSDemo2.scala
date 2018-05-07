package util.processOS

import scala.sys.process._

/**
  * Created by yidxue on 2018/3/30
  */
object ScalaOSDemo2 {

  def main(args: Array[String]): Unit = {
    val command = Seq(
      "sqoop", "import", "-Dhbase.zookeeper.quorum=localhost",
      "--connect", "jdbc:oracle:thin:@localhost:1521:dbName",
      "--username", "xxx",
      "--password", "xxx",
      "--query", """"select col1, col2, col3 from table1 and $CONDITIONS"""",
      "--fields-terminated-by", """"\t"""",
      "--lines-terminated-by", """"\n"""",
      "--hbase-table", "SITEINFO",
      "--column-family", "INFO",
      "--hbase-row-key", "SITEID",
      "-m", "1",
      "--hbase-create-table"
    )

    println(command.mkString(" "))
    command.!
  }
}
