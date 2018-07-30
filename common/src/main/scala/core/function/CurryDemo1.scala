package core.function

/**
  * Created by yidxue on 2018/7/28
  */
object CurryDemo1 {

  def multiply(m: Int)(n: Int): Int = m * n

  def main(args: Array[String]): Unit = {
    // 输出
    println(multiply(2)(3))

    // 返回一个函数
    val timesTwo1 = multiply(2) _
    println(timesTwo1(4))

    // 返回一个函数
    val timesTwo2 = multiply(2): (Int) => Int
    println(timesTwo2(4))

    // 返回一个函数
    val timesTwo3 = multiply(2): Function1[Int, Int]
    println(timesTwo3(4))

    val timesTwo4 = multiply _
    println(timesTwo4)
  }
}
