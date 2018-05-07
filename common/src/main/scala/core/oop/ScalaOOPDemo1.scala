package util.oop

class Customer(var name: String, var age: Int, var card: CardInfo) {

  def this() {
    this("None", 0, new CardInfo)
  }

  override def toString = s"customer name is $name, age is $age, hold card is $card"
}

class CardInfo(var id: String, var ctype: String) {

  def this(ctype: String) {
    this("12345", ctype)
  }

  def this() {
    this("借记卡")
  }

  override def toString = s"(银行卡id: $id, 银行卡type: $ctype)"
}

object ScalaOOPDemo {

  def main(args: Array[String]): Unit = {
    val attBean = new Customer("erwin", 26, new CardInfo)
    println(attBean.card)
    println(attBean)

    attBean.card = new CardInfo("信用卡")
    println(attBean.card)
    println(attBean)

    attBean.card = new CardInfo("23456", "信用卡")
    println(attBean.card)
    println(attBean)

    println("=======================")
    val attBean1 = new Customer()
    println(attBean1.card)
    println(attBean1)
  }
}