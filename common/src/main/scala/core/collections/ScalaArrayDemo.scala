package core.collections

/**
  * User: Erwin
  * Date: 17/12/28 上午9:47
  * Description: 
  */
object ScalaArrayDemo {

  def main(args: Array[String]): Unit = {
    val arr = new Array[String](3)
    // 下标从0开始
    arr(0) = "0"
    arr(1) = "1"
    arr(2) = "2"
    println(arr.mkString(","))

    arr.foreach(println(_))
  }
}
