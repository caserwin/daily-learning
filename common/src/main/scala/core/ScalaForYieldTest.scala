package core

/**
  * User: Erwin
  * Date: 17/11/22 下午12:44
  * Description: 
  */
object ScalaForYieldTest {

  def main(args: Array[String]): Unit = {

    val a = Array(1, 2, 3, 4, 5)
    val b = for (e <- a if e > 2 if e < 4) yield e
    b.foreach(println(_))
  }
}
