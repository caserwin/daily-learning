package core.workflow.timeout

import java.util.concurrent._
import workflow.timeout.runable.TaskRun

/**
  * Created by yidxue on 2018/3/29
  */
object TimeoutDemo1 {

  def main(args: Array[String]): Unit = {
    val executor = Executors.newSingleThreadExecutor
    val future = executor.submit(new TaskRun)
    try {
      future.get(100, TimeUnit.SECONDS)
    } catch {
      case _: TimeoutException =>
        // mayInterruptIfRunning设成false话，不允许在线程运行时中断，设成true的话就直接终端。
        future.cancel(true)
        System.out.println("it is time out!")
      case e@(_: InterruptedException | _: ExecutionException) =>
        e.printStackTrace()
    }
    executor.shutdown()
  }
}
