package core.regex

/**
  * User: Erwin
  * Date: 17/11/24 下午3:59
  * Description: 
  */
object ScalaRegexDemo2 {

  def main(args: Array[String]): Unit = {
    // demo1：在给定字符串中找出所有符合正则表达的字符子串。
    println("============== demo1 ===============")
    val pattern1 = "(s|S)cala".r
    val str1 = """scala is scalable cool tools"""
    println(pattern1.findAllMatchIn(str1).mkString(", "))
    println(pattern1.findAllIn(str1).mkString(", "))
    println(pattern1.findFirstIn(str1).mkString(", "))
    println(pattern1.findFirstMatchIn(str1).mkString(", "))

    // demo2：模式匹配捕获
    println("============== demo2 ===============")
    val str2 = "one493two483three"
    val pattern2 = """two[\d]+three""".r
    pattern2.findAllIn(str2).foreach(println)

    // demo3: 根据 unapply 正则捕获
    println("============== demo3 ===============")
    val str3 = "one493two483three erwin tttt"
    val pattern3 =""".*two(\d+)three ([a-z]+).*""".r
    val pattern3(num, str) = str3
    println(num, str)

    // demo4: 根据 match...group...，进行捕获
    println("============== demo4 ===============")
    val str4 = "one493two483three"
    val pattern4 = """two(\d+)three""".r
    pattern4.findAllIn(str4).matchData.foreach { m => println(m.group(1)) }

    // demo5: 根据模式匹配，进行捕获
    println("============== demo5 ===============")
    val str5 = "one493two483three"
    val pattern5 = """(?<=two)\d+(?=three)""".r
    pattern5.findAllIn(str5).foreach(println)

    // demo6: 根据模式匹配，进行捕获
    println("============== demo6 ===============")
    val str6 = "one493two483three"
    val pattern6 = """two(\d+)three""".r
    println(pattern6.findAllIn(str6).matchData.map(m => m.group(1)).mkString(""))

    // demo7:
    println("============== demo7 ===============")
    val str7 = "Android/10.0 Mozilla/5.0(Linux; U; Android 7.0; en-GB; samsung_dreamltexx_24_7.0_10.0.0.21000142_21000142 B"
    val AndroidPattern = "Android/([0-9\\.]+)".r
    println(AndroidPattern.findAllIn(str7).matchData.map(m => m.group(1)).mkString(""))

  }
}
