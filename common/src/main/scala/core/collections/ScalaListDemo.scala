package core.collections

import scala.collection.mutable
//import scala.collection.mutable.MutableList

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

    // List 添加元素，然后赋值给新的常量y、z
    val y = x.+:(2)
    val z = x.:+(3)
    println(y)
    println(z)

    // 两个列表合并
    println(y ++ z)
    println(y ::: z)

    // 定义一个可变列表, 只能存放Int类型
    val mlsInt = mutable.MutableList[Int](1, 2, 3, 4)
    mlsInt += 5
    println(mlsInt)

    // 定义一个可变列表, 可以存放任何类型
    val mls = mutable.MutableList(1, 2, 3, 4, "str")
    mls += 5
    println(mls)

  }
}
