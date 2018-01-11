package core

import java.text.SimpleDateFormat
import java.util.Date

/**
  * User: Erwin
  * Date: 17/12/11 下午3:12
  * Description: 
  */
object ScalaDateDemo {

  def main(args: Array[String]): Unit = {
    val start = new Date()
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    println(dateFormat.format(start))
  }
}
