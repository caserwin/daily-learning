package core.concurrentdemo

import java.util.Random
import java.util.concurrent.{CountDownLatch, TimeUnit}

/**
  * Created by yidxue on 2018/3/5
  */
class Worker(downLatch: CountDownLatch, name: String) extends Runnable {

  override def run(): Unit = {
    this.doWork()
    System.out.println(this.name + "活干完了！")
    this.downLatch.countDown()
  }

  private def doWork(): Unit = {
    System.out.println(this.name + "正在干活!")
    TimeUnit.SECONDS.sleep(new Random().nextInt(10))
  }
}
