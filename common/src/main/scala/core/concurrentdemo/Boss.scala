package core.concurrentdemo

import java.util.concurrent.CountDownLatch

/**
  * Created by yidxue on 2018/3/5
  */
class Boss(downLatch: CountDownLatch) extends Runnable {

  override def run(): Unit = {
    System.out.println("老板正在等所有的工人干完活......")
    this.downLatch.await()
    System.out.println("工人活都干完了，老板开始检查了！")
  }
}
