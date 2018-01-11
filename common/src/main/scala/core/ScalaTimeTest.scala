package core

import java.text.SimpleDateFormat

/**
  * User: Erwin
  * Date: 17/11/17 下午4:57
  * Description: 
  */
object ScalaTimeTest {

  val safeFormatter: ThreadLocal[SimpleDateFormat] = new ThreadLocal[SimpleDateFormat]() {
    override def initialValue(): SimpleDateFormat = {
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    }
  }

  def main(args: Array[String]): Unit = {
    println(safeFormatter.get().parse("2017-11-01 00:00:00").getTime)
    println(safeFormatter.get().parse("2017-11-1 00:00:00").getTime)
  }
}
