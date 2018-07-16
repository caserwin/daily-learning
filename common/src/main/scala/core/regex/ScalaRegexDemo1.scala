package core.regex

/**
  * Created by yidxue on 2018/7/15
  * 直接使用Java 的正则表达
  */
object ScalaRegexDemo1 {

  def main(args: Array[String]): Unit = {
    val m = java.util.regex.Pattern.compile("msie ([0-9\\.]+)").matcher("this is msie 10.0 windows")
    if (m.find) {
      println(m.group)
    }
  }
}
