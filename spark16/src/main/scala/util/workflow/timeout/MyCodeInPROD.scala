package util.workflow.timeout

import java.util.concurrent.{Executors, TimeUnit, TimeoutException}
import org.apache.log4j.{LogManager, Logger}

/**
  * Created by yidxue on 2018/4/2
  * 不能运行，实际用的框架而已
  */
object MyCodeInPROD {
  val logger: Logger = LogManager.getRootLogger

  def main(args: Array[String]): Unit = {
    val delaySecond = 1200

    // 提交jmt spark job
    val executor = Executors.newSingleThreadExecutor()
    val future = executor.submit(new BatchAnalysisRun("2018-03-30", ""))

    // 再起一个job，延迟 delaySecond 执行kill之前的job。
    try {
      future.get(delaySecond, TimeUnit.SECONDS)
    } catch {
      case _: TimeoutException =>
        // PhoenixJdbcUtil.insertTelemetryData(Constant.OUT_ZK, "miniJMT", jobStartTime, "0", "0", s"time out ${delaySecond}s", timeRange(0), Constant.OUT_SAP_TELEMETRY)
        future.cancel(true)
      case _: Throwable =>
      // PhoenixJdbcUtil.insertTelemetryData(Constant.OUT_ZK, "miniJMT", jobStartTime, "0", "0", s"other errors", timeRange(0), Constant.OUT_SAP_TELEMETRY)
    }

    executor.shutdown()
  }
}
