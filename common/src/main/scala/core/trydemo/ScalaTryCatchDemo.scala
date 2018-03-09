package core.trydemo

/**
  * Created by yidxue on 2018/3/8
  * reference: https://windor.gitbooks.io/beginners-guide-to-scala/content/chp6-error-handling-with-try.html
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
    val youngCustomer = Customer(15)
    try {
      println(buyCigarettes(youngCustomer))
    } catch {
      case UnderAgeException(msg) => println(msg)
      case _ => println("this is other exception")
    }
  }
}
