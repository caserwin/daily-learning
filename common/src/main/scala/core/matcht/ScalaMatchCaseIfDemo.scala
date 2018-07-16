package core.matcht

object ScalaMatchCaseIfDemo {

  def main(args: Array[String]): Unit = {
    val ETypes = "dd ee ff"
    val Ref6s = "11 22 33"

    val joinMethod = (ETypes, Ref6s) match {
      case (ETypes, Ref6s) if ETypes.contains("bb") => "Plugin"
      case (ETypes, Ref6s) if ETypes.contains("aa") => "Download"
      case (ETypes, Ref6s) if ETypes.contains("dd") => "ActiveX"
      case (ETypes, Ref6s) if ETypes.contains("ee") => "Extension"
    }
    println(joinMethod)
  }
}
