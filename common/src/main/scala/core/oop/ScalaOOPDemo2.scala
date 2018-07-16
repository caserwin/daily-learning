package core.oop

class EventLog(var et:String, var timestamp:String, var name:String){
  def this() {
    this("1","2","3")
  }
}

object ScalaOOPDemo2{
  def main(args: Array[String]): Unit = {
    val log = new EventLog()
    println(log.et)
    log.et="no log ouput!"
    println(log.et)
  }
}
