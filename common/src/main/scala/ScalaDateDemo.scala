import java.text.SimpleDateFormat
import java.util.Date

object ScalaDateDemo {

  def main(args: Array[String]): Unit = {
        val curDate= new SimpleDateFormat("yyyy-MM-dd").format(new Date)
        println(curDate)
  }
}
