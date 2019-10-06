package core

import java.text.SimpleDateFormat
import java.util.Date
import tools.DateUtil

/**
  * User: Erwin
  * Date: 17/11/17 下午4:57
  * Description: 
  */
object ScalaDateDemo {

  val safeFormatter: ThreadLocal[SimpleDateFormat] = new ThreadLocal[SimpleDateFormat]() {
    override def initialValue(): SimpleDateFormat = {
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    }
  }

  def main(args: Array[String]): Unit = {
    // get timestamp
    println(safeFormatter.get().parse("2017-11-01T00:00:00").getTime)
    println(safeFormatter.get().parse("2017-11-1T00:00:00").getTime)

    // format date
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    println(dateFormat.format(new Date()))
    println(dateFormat.format(new Date()))
    println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))

    // 日期比较大小
    val format = safeFormatter.get()
    println(format.parse("2000-01-01T00:00:00").getTime > format.parse(" 1999-01-01T00:00:00").getTime)

    //
    val a = dateFormat.parse(dateFormat.format(new Date())).getTime.toString
    println(a.substring(0, 10).toLong)

    // 日期格式化后加减操作
    println(DateUtil.getAfterDay("2018-05-07", "yyyy-MM-dd", "yyyyMMdd"))
    println((safeFormatter.get().parse("2017-11-01T00:00:00").getTime - safeFormatter.get().parse("2017-11-00T00:00:00").getTime) / 3600000)
  }
}
