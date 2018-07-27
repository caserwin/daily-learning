package core.function

/**
  * Created by yidxue on 2018/7/24
  */
object FunctionDemo1 {
  //方法写法
  def m(x: Int): Int = {
    2 * x
  }

  // 函数写法
  def f: Function1[Int, Int] = {
    (x: Int) => 2 * x
  }

  def main(args: Array[String]): Unit = {
    println("方法输出：" + m(2))
    println("函数输出：" + f(2))

    println("打印一个函数：" + f)
    // println("打印一个方法：" + m) //编译都不过

    // 把一个方法转成函数
    val f1: Function1[Int, Int] = m
    println("把方法转成函数后，输出：" + f1)
    val f2: (Int) => Int = m
    println(f2)
  }
}
