package phoenix

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
    val spark = SparkSession.builder().appName("Spark SQL basic example").config("spark.master", "local[*]").getOrCreate()

    val df = {
      val configuration = new Configuration
      configuration.set("hbase.zookeeper.quorum", "localhost:2181")
      new SparkSqlContextFunctions(spark.sqlContext).phoenixTableAsDataFrame("SAP_MEETINGJMT", columns = Seq("UID_", "GID"), predicate = Some("rowkey like '2017-10-10%'"), conf = configuration)
    }

    df.show()
    spark.stop()
  }
}
