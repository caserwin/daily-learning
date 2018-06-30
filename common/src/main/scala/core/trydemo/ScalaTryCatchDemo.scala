package core.trydemo

/**
  * Created by yidxue on 2018/3/8
  * reference: https://windor.gitbooks.io/beginners-guide-to-scala/content/chp6-error-handling-with-try.html
  * 一般都是这种用法
  */
object ScalaTryCatchDemo {

  case class Customer(age: Int)

  case class UnderAgeException(message: String) extends Exception(message)

  def buyCigarettes(customer: Customer): Int = {
    if (customer.age < 16) {
      throw UnderAgeException(s"Customer must be older than 16 but was ${customer.age}")
    }
    else {
      customer.age
    }
  }

  def main(args: Array[String]): Unit = {
    val youngCustomer = Customer(18)
    println("=============demo1=============")
    val out = try {
      buyCigarettes(youngCustomer)
    } catch {
      case UnderAgeException(msg) => "success"
      case e: Throwable => "fail"
    }

    println(out)
    println("=============demo2=============")
    try {
      println(buyCigarettes(youngCustomer))
    } catch {
      case UnderAgeException(msg) => println("success")
      case e: Throwable => println("fail")
    }
  }
}
