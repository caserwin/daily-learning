package core.implicitdemo

import scala.language.implicitConversions
/**
  * Created by yidxue on 2018/7/15
  * 参数隐式转换
  */
object ScalaImplicitDemo2 {

  def main(args: Array[String]): Unit = {
    implicit val a: Int = 2
    println(fun(1))
  }

  def fun(x: Int)(implicit y: Int): Int = {
    x + y
  }
}
