package core.oop

import java.text.SimpleDateFormat

object Test2 {

  val safeFormatter: ThreadLocal[SimpleDateFormat] = new ThreadLocal[SimpleDateFormat](){
    override def initialValue(): SimpleDateFormat = {
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    }
  }

  def main(args: Array[String]): Unit = {
//    val str = "12345"
//    val s ="1213"
//
//    println(str.contains(s))
    val format = safeFormatter.get()
    println(format.parse("2000-01-01 00:00:00").getTime>format.parse(" 1999-01-01 00:00:00").getTime)
  }
}
