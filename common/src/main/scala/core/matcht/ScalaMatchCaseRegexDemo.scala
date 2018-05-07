package util.matcht

import scala.util.matching.Regex

/**
  * Created by yidxue on 2018/1/31
  */
object ScalaMatchCaseRegexDemo {

  private val Pattern1 = "firefox/([0-9]+)".r
  private val Pattern2 = "chrome/([0-9]+)".r
  private val Pattern3 = "android/([0-9\\.]+)".r

  def main(args: Array[String]): Unit = {
    println(getVer("firefox/62"))
    println(getVer("chrome/57"))
    println(getVer("android/10"))
    println(getVer("xfd"))
  }

  def getVer(key: String): Any = key match {
    case Pattern1(version) => version
    case Pattern2(version) => version
    case Pattern3(version) => version
    case _ => "unknown"
  }
}
