package core.implicitdemo

import scala.language.implicitConversions

/**
  * 类型隐式转换
  */
object ScalaImplicitDemo1 {

  implicit def int2Range(num: Int): Range = 1 to num

  def main(args: Array[String]): Unit = {
    println(spreadNum(5))
  }

  def spreadNum(range: Range): String = {
    range.mkString(",")
  }
}
