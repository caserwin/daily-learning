package util.lambda

object LambdaDemo1 {
  def main(args: Array[String]): Unit = {
    // 定义一个普通函数
    def foo(s: String): Unit = {println(s)}
    // 定义一个高阶函数
    def hf(f: String => Unit): Unit = {f("higher")}
    // 传入一个普通函数
    hf(foo)
    // 传入一个lambda表达式写的匿名函数
    hf((s: String) => {println(s)})
    // 简化lambda表达式，省略输入参数s类型，省略输出的花括号
    // 省略了s的类型，这是因为在定义hf时已经声明了参数类型为String=>Unit，编译器会把lambda表达式中的入参和出参按声明的类型来对待
    hf(s=>println(s))
    // 采用了占位符的方式。它的形式为，对于所有的 x=>g(x) 都可以用占位符的形式写为：g(_)，相当于省去了lambda表达式的入参和箭头部分，然后用占位符表示入参
    hf(println(_))
    // 再简化的版本，省略圆括号
    hf(println _)
    // 更简化的版本
    // 简单的说就是对于lambda表达式中只有一个参数，并且箭头右边的逻辑是对入参执行一个函数：即 x => f(x)，则可以简写为f
    hf(println)
  }
}
