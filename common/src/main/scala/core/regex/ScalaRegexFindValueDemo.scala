package util.regex

/**
  * Created by yidxue on 2018/1/31
  */

import scala.util.control.Breaks._
import scala.util.matching.Regex

object ScalaRegexFindValueDemo {

  private val IEPattern = "msie ([0-9\\.]+)".r
  private val TridentPattern = "trident.*rv:([0-9\\.]+)".r
  private val ChromePattern = "chrome/([0-9]+\\.[0-9]+)".r
  private val FirefoxPattern = "firefox/([0-9]+\\.[0-9]+)".r
  private val SafariPattern = "version/([0-9]+\\.[0-9]+).*safari".r

  def getVersion(browser: String, userAgent: String): String = {
    if ("Other".equals(browser) || "None".equals(browser)) return "Other"

    var version = (browser.toLowerCase, userAgent.toLowerCase) match {
      case (bro, ua) if bro.equals("ie") => getResByRegex(List(IEPattern, TridentPattern), ua)
      case (bro, ua) if bro.equals("chrome") => getResByRegex(List(ChromePattern), ua)
      case (bro, ua) if bro.equals("firefox") => getResByRegex(List(FirefoxPattern), ua)
      case (bro, ua) if bro.equals("safari") => getResByRegex(List(SafariPattern), ua)
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
    println(getVersion("ie", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko"))
  }
}
