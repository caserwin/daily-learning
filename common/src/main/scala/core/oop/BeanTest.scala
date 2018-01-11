package core.oop

class Customer(var name: String,var age:Int,var card:CardInfo) {
  def this() {
    this("None", 0, new CardInfo)
  }

  override def toString = s"customer name is $name, age is $age, hold card is $card"
}

class CardInfo (var id :String, var ctype: String) {
  def this(ctype: String) {
    this("card_id1",ctype)
  }

  def this() {
    this("card_type1")
  }

  override def toString = s"(id: $id, ctype: $ctype)"
}

object Main{

  def main(args: Array[String]): Unit = {
    var attBean = new Customer("erwin", 26, new CardInfo)
    attBean.card = new CardInfo("cisco_12345", "rd_type")
    println(attBean.card)
    println(attBean)
  }
}