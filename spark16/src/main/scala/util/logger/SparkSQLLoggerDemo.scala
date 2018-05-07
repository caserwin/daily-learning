package util.logger

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by yidxue on 2018/3/9
  */
object SparkSQLLoggerDemo {

  var logger: Logger = LoggerFactory.getLogger("SparkSQLLoggerDemo")

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val dataSeq1 = Seq(
      ("1", "zhangsan", ""),
      ("2", "lisi", "beijing"),
      ("3", "wangwu", "shanghai")
    )
    val inputDF = sc.parallelize(dataSeq1).toDF("id", "name", "city")
    inputDF.show()

    logger.info("logger:  job finish.....")
  }
}
