package util.collections

/**
  * User: Erwin
  * Date: 17/11/28 上午9:29
  * Description: 
  */
object ScalaListDemo {

  /**
    * 1. Scala 列表类似于数组，它们所有元素的类型都相同，但是它们也有所不同：列表是不可变的，值一旦被定义了就不能改变
    * 2. mutable.MutableList() 是可变列表
    */

  def main(args: Array[String]): Unit = {
    // 定义一个List
    val x = List(1)

    // List 添加元素
    val y = x.+:(2)
    val z = x.:+(3)
    println(y)
    println(z)

    // List 添加元素
    val y1 = 2 +: x
    val z1 = x :+ 3
    println(y1)
    println(z1)

    // 两个列表合并
    println((y ++ z).mkString(","))
    println((y ::: z).mkString(","))

  }
}
