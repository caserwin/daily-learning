package core.oop

class EventLog(var eventtype:String, var timestamp:String, var remotehost:String){
  def this() {
    this("1","2","3")
  }
}

object ScalaOOPDemo2{
  def main(args: Array[String]): Unit = {
    val aa = new EventLog
    println(aa.eventtype)
    aa.eventtype="sss"
    println(aa.eventtype)
  }
}
