package core.function

/**
  * Created by yidxue on 2018/7/28
  */
object CurryDemo2 {

  def multiply: (Int) => ((Int) => Int) = {
    (a: Int) => { (x: Int) => a * x }
  }

  def main(args: Array[String]): Unit = {
    // 返回一个函数
    val timesTwo1 = multiply(2)
    println(timesTwo1(4))

    println(multiply(4)(2))
  }
}
