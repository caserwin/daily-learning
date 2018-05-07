package util.matcht

import scala.util.control.Breaks._
import scala.util.matching.Regex

/**
  * User: Erwin
  * Date: 17/11/24 下午2:52
  * Description:
  */
object ScalaMultiMatchCaseDemo {

  private val WindowsPattern = "windows nt ([0-9\\.]+)".r
  private val MacOSPattern = "mac os x ([0-9]+_[0-9]+)".r
  private val AndroidPattern = "android/([0-9]+\\.[0-9]+)".r

  def getVersion(platform: String, userAgent: String): String = {
    if ("Other".equals(platform) || "None".equals(platform)) return "Other"

    var version = (platform.toLowerCase, userAgent.toLowerCase) match {
      case (os, ua) if os.equals("windows") => getResByRegex(List(WindowsPattern), ua) match {
        case "5.0" => "2000"
        case "5.1" => "XP"
        case "5.2" => "2003 or XP64bit"
        case "6.0" => "Vista"
        case "6.1" => "7.0"
        case "6.2" => "8.0"
        case "6.3" => "8.1"
        case "6.4" => "8.2"
        case "10.0" => "10.0"
        case _ => ""
      }
      case (os, ua) if os.equals("mac") => getResByRegex(List(MacOSPattern), ua).replace("_", ".")
      case (os, ua) if os.equals("android") => getResByRegex(List(AndroidPattern), ua)
      case _ => ""
    }

    version = if ("".equals(version)) "Other" else version
    version
  }

  private def getResByRegex(list: List[Regex], ua: String): String = {
    var resVersion = ""
    breakable {
      for (regex <- list) {
        resVersion = regex.findAllIn(ua).matchData.map(m => m.group(1)).mkString("")
        if (!"".equals(resVersion)) break
      }
    }
    resVersion
  }

  def main(args: Array[String]): Unit = {
    println(getVersion("android", "Android/10.0 Mozilla/5.0(Linux; U; Android 7.0; en-GB; samsung_dreamltexx_24_7.0_10.0.0.21000142_21000142 B"))
  }
}