package core

/**
  * User: Erwin
  * Date: 17/12/11 下午4:00
  * Description: 
  */

import java.security.MessageDigest

object ScalaHashMD5Demo {


  /**
    * md5 code from https://stackoverflow.com/questions/5992778/computing-the-md5-hash-of-a-string-in-scala
    *               https://stackoverflow.com/questions/38855843/scala-one-liner-to-generate-md5-hash-from-string
    */
  def md5(s: String): String = {
    MessageDigest.getInstance("MD5").digest(s.getBytes).map(0xFF & _).map {
      "%02x".format(_)
    }.foldLeft("") {
      _ + _
    }
  }

  def main(args: Array[String]): Unit = {
    println(md5("Hello"))
    println(md5("Hello1"))

  }
}
