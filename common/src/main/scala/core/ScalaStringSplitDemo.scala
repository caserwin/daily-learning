package core

/**
  * User: Erwin
  * Date: 17/12/18 上午9:44
  * Description: 
  */
object ScalaStringSplitDemo {

  def main(args: Array[String]): Unit = {
    val str = "2017-12-17 00:05:42"

    println(str.split("\\s+")(0))
  }
}
