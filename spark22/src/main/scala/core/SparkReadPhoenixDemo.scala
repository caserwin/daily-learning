package util

import org.apache.hadoop.conf.Configuration
import org.apache.phoenix.spark._
import org.apache.spark.sql.SparkSession

/**
  * User: Erwin
  * Date: 17/12/19 下午12:12
  * Description: 
  */

object SparkReadPhoenixDemo {

  def main(args: Array[String]): Unit = {

    val spSess = SparkSession.builder().appName("Spark SQL basic example").config("spark.master", "local[*]").getOrCreate()
    import spSess.implicits._

    val jmtDF = {
      val configuration = new Configuration
      configuration.set("hbase.zookeeper.quorum", "rpbt1hmn001.webex.com:2181")
      new SparkSqlContextFunctions(spSess.sqlContext).phoenixTableAsDataFrame("SAP_MEETINGJMF", columns = Seq("CONFID", "UID_", "GID", "TIMESTAMP"), predicate = Some("rowkey between '2017-12-05' and '2017-12-06'"), conf = configuration)
    }

    jmtDF.select(
      $"CONFID",
      $"UID_",
      $"GID",
      $"TIMESTAMP"
    ).limit(10).show()
  }
}
