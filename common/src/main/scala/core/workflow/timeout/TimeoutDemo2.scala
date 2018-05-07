package util.workflow.timeout

import java.util.concurrent.{Executors, TimeUnit}
import workflow.timeout.runable.TaskRun

/**
  * Created by yidxue on 2018/3/28
  */
object TimeoutDemo2 {

  def main(args: Array[String]): Unit = {
    val executor = Executors.newScheduledThreadPool(2)
    val future = executor.submit(new TaskRun)
    // 再起一个job，延迟5秒执行kill之前的job。
    executor.schedule(new Runnable() {
      override def run(): Unit = {
        future.cancel(true)
      }
    }, 5, TimeUnit.SECONDS)

    // NOTICE: shutdownNow() 和 shutdown() 有什么区别？？
    executor.shutdown()
  }
}
