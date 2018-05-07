package core

import java.text.SimpleDateFormat
import java.util.Date

import org.slf4j.{Logger, LoggerFactory}

/**
  * User: Erwin
  * Date: 17/11/17 下午4:57
  * Description: 
  */
object ScalaDateDemo {

  val safeFormatter: ThreadLocal[SimpleDateFormat] = new ThreadLocal[SimpleDateFormat]() {
    override def initialValue(): SimpleDateFormat = {
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    }
  }

  def main(args: Array[String]): Unit = {
    // get timestamp
    println(safeFormatter.get().parse("2017-11-01 00:00:00").getTime)
    println(safeFormatter.get().parse("2017-11-1 00:00:00").getTime)

    // format date
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    println(dateFormat.format(new Date()))

    //
    val format = safeFormatter.get()
    println(format.parse("2000-01-01 00:00:00").getTime > format.parse(" 1999-01-01 00:00:00").getTime)
  }
}
