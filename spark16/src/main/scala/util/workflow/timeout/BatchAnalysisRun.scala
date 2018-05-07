package util.workflow.timeout

/**
  * Created by yidxue on 2018/4/2
  */
import org.apache.log4j.{LogManager, Logger}
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/3/28
  */
class BatchAnalysisRun(start: String, jobStartTime: String) extends Runnable {
  val logger: Logger = LogManager.getRootLogger

  override def run(): Unit = {
    val sparkConf = new SparkConf().setAppName("JMTAndJMSMiniBatchAnalysisApp")
    sparkConf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    sparkConf.set("spark.network.timeout", "3600")
    sparkConf.set("spark.yarn.executor.memoryOverhead", "8192")

    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)
    sqlContext.sql("set spark.sql.shuffle.partitions=50")

    /* stop spark job. NOTICE: it is important to stop a spark job */
    sc.stop()
  }
}
