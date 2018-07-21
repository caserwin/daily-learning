package core.collections.listAndSeq

import scala.language.implicitConversions

/**
  * User: Erwin
  * Date: 17/11/28 上午9:29
  * Description: 
  */
object ScalaListDemo {

  def main(args: Array[String]): Unit = {
    // 定义一个List
    val x = List(1, 2, 3, 4)

    // List 添加元素，并赋值给新的List
    val y = x.+:(2)
    val z = x.:+(3)
    println("头部添加一个元素：" + y)
    println("头部添加一个元素：" + (2 +: x))
    println("尾部添加一个元素：" + z)
    println("尾部添加一个元素：" + (x :+ 3))

    // 两个集合合并
    println((y ++ z).mkString(","))

    // 根据index 查询
    println("输出第一个元素：" + x.head)
    println("输出第二个元素：" + x(1))
    println("输出第二个元素：" + x.apply(1))

    // 删除元素
    println("删除第一个元素后的集合: " + x.drop(1))

    // 判断是否包含某个元素
    val result1 = if (x.contains(4)) "true" else "false"
    println("判断是否包含某个元素：" + result1)

    // 返回最小最大值
    println("集合最小值为：" + x.min)
    println("集合最大值为：" + x.max)
    println("集合元素sum为：" + x.sum)

    // sliding 用法 demo
    println(x.sliding(2).map(a => "(" + a.head + "," + a(1) + ")").toArray.mkString(","))

    // 集合反转
    println("集合反转: " + x.reverse)
  }
}
