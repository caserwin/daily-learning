package core.matcht

/**
  * User: Erwin
  * Date: 17/12/5 上午10:48
  * Description: 
  */
object ScalaMatchCaseValueDemo {

  def main(args: Array[String]): Unit = {
    val JMTEventTypes = "1"
    val JMTRefnum6s = "b"

    (JMTEventTypes, JMTRefnum6s) match {
      case ("1", "a") => println("=====1====")
      case ("1", "b") => println("=====2====")
      case ("2", "a") => println("=====3====")
      case ("2", "b") => println("=====4====")
      case _ => println("=====no match====")
    }
  }
}
