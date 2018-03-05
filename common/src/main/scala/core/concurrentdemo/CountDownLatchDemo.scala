package core.concurrentdemo

import java.util.concurrent.{CountDownLatch, Executors}

/**
  * Created by yidxue on 2018/3/5
  */
object CountDownLatchDemo {

  def main(args: Array[String]): Unit = {
    val executor = Executors.newCachedThreadPool
    val latch = new CountDownLatch(3)
    val w1 = new Worker(latch, "张三")
    val w2 = new Worker(latch, "李四")
    val w3 = new Worker(latch, "王二")

    val boss = new Boss(latch)

    executor.execute(w3)
    executor.execute(w2)
    executor.execute(w1)
    executor.execute(boss)

    executor.shutdown()
  }
}
