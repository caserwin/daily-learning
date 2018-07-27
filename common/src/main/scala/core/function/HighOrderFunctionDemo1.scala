package core.function

/**
  * Created by yidxue on 2018/7/25
  */
object HighOrderFunctionDemo1 {

  def main(args: Array[String]): Unit = {
    // 定义一个普通函数
    val f: (Int, Int) => Int = { (x: Int, y: Int) => x + y }

    // 一个以函数作为参数的方法
    def hof1(f: (Int, Int) => Int): Int = {
      f(1, 2)
    }

    // 高阶函数定义方式二
    def hof2: Function1[Function2[Int, Int, Int], Int] = {
      (f: Function2[Int, Int, Int]) => f(1, 2)
    }

    // 高阶函数定义方式三
    def hof3: (Function2[Int, Int, Int]) => Int = {
      (f: (Int, Int) => Int) => f(1, 2)
    }

    // 高阶函数定义方式四
    def hof4: ((Int, Int) => Int) => Int = {
      (f: (Int, Int) => Int) => f(1, 2)
    }

    println(hof1(f))
    println(hof2(f))
    println(hof3(f))
    println(hof4(f))

    //    println(hof1)  // 不能打印，会报错
    println(hof2)
    println(hof3)
    println(hof4)

    val ahof1 = hof1: Function1[Function2[Int, Int, Int], Int]
    println(ahof1)
    println(ahof1(f))


  }
}
