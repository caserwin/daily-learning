package util.matcht

/**
  * User: Erwin
  * Date: 17/11/28 上午10:24
  * Description:
  */
object ScalaMatchCaseTypeDemo {

  def main(args: Array[String]): Unit = {
    println(userMatch("1"))
  }

  def userMatch(x: Any): Any = x match {
    case s: String => "Type is string"
    case y: Int => "Type is int"
    case _ => "Type is other"
  }
}
