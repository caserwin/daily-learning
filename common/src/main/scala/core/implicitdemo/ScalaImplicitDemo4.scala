package core.implicitdemo

import scala.language.implicitConversions

/**
  * Created by yidxue on 2018/5/22
  * 通过隐式转换实现反射。
  */
object ScalaImplicitDemo4 {

  case class Caller[T >: Null <: AnyRef](klass: T) {
    def call(methodName: String, args: AnyRef*): AnyRef = {
      def argtypes = args.map(_.getClass)

      def method = klass.getClass.getMethod(methodName, argtypes: _*)

      method.invoke(klass, args: _*)
    }
  }

  object UserMethod {
    def method1(num: String): Unit = {
      println(s"this is method $num")
    }

    def method2(): Unit = {
      println("this is method 2")
    }
  }


  def main(args: Array[String]): Unit = {
    implicit def anyref2callable[T >: Null <: AnyRef](klass: T): Caller[T] = Caller(klass)

    UserMethod.call("method1", "1")
    UserMethod.call("method2")
  }
}
