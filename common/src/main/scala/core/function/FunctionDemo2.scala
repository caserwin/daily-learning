package core.function

/**
  * Created by yidxue on 2018/7/24
  */
object FunctionDemo2 {

  // 方法
  def m(x: Int): Int = {
    2 * x
  }

  // 普通函数
  def f: Int => Int = { (x: Int) =>
    2 * x
  }

  def hf(f1: Int => Int): Int = {
    f1(2)
  }

  def main(args: Array[String]): Unit = {
    println(hf(m))
    println(hf(f))
  }
}
