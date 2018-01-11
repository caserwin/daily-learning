package core

/**
  * User: Erwin
  * Date: 17/11/28 上午10:24
  * Description:
  */
object ScalaMatchCaseTest2 {

  implicit class Regex(sc: StringContext) {
    def r = new util.matching.Regex(sc.parts.mkString, sc.parts.tail.map(_ => "x"): _*)
  }

  def main(args: Array[String]): Unit = {

    val res1 = "123" match {
      case r"d+" => true
      case _ => false
    }
    println(res1)


    "123" match { case r"(d+)$d" => d.toInt case _ => 0 }

  }
}
