package core.collections

import Array._

/**
  * User: Erwin
  * Date: 17/12/28 上午9:47
  * Description: https://www.yiibai.com/scala/scala_arrays.html
  */
object ScalaArrayDemo {

  def main(args: Array[String]): Unit = {
    // 声明数组
    val arr = new Array[String](2)
    // 声明数组时赋值
    val arr1 = Array("2", "3", "4")

    // 赋值
    arr(0) = "0"
    arr(1) = "1"
    println(arr.mkString(","))

    // sliding 用法 demo
    println(arr1.sliding(2).map(a => a(0) + "," + a(1)).toArray.mkString(","))

    // concat拼接两个数组。
    println(concat(arr, arr1).mkString(","))

    // 更新数组元素
    arr.update(0, "00")
    println(arr.mkString(","))

    // 基于range 创建数组
    println(range(10, 20, 2).mkString(","))

    println("数组中最小元素：" + arr1.min)
    println("数组中最大元素：" + arr1.max)
  }
}