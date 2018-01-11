package core

object ScalaMatchCaseTest {

  def main(args: Array[String]): Unit = {


    val JMTEventTypes = "dd ee ff"
    val JMTRefnum6s ="11 22 33"

    val joinMethod = (JMTEventTypes,JMTRefnum6s) match {
      case (JMTEventTypes,JMTRefnum6s) if JMTEventTypes.contains("bb") => "Plugin"
      case (JMTEventTypes,JMTRefnum6s) if JMTEventTypes.contains("aa") => "JavaDownload"
      case (JMTEventTypes,JMTRefnum6s) if JMTEventTypes.contains("dd") => "ActiveX"
      case (JMTEventTypes,JMTRefnum6s) if JMTEventTypes.contains("ee") => "Extension"
    }

    println(joinMethod)

  }
}
