package core.oop

//case class EventLog(eventtype:String, timestamp:String, remotehost:String, useragent:String, refnum1:String, refnum6:String, refnum3:String)
class EventLog(var eventtype:String, var timestamp:String, var remotehost:String){
  def this() {
    this("1","2","3")
  }
}

object Test1{
  def main(args: Array[String]): Unit = {
    val aa = new EventLog
    println(aa.eventtype)
    aa.eventtype="sss"
    println(aa.eventtype)
  }
}
