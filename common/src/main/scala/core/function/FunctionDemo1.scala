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
  def f: Int => Int = { (x: Int) =>
    2 * x
  }

  def main(args: Array[String]): Unit = {
    println(m(2))
    println(f(2))

    println(f)
    //    println(m) //编译都不过
  }
}
