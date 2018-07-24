package util.conf

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by yidxue on 2018/5/31
  */
object SparkConfDemo {
  val conf: Config = ConfigFactory.load()
  var logger: Logger = LoggerFactory.getLogger("SparkConfDemo")

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("JMTAndJMSBatchAnalysisApp")
    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)

    logger.info("=================================")
    logger.info(conf.getString("test.output"))
    logger.info("=================================")
  }
}
