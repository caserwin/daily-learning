package core.json

/**
  * User: Erwin
  * Date: 17/12/5 上午9:26
  * Description: 
  */
import com.google.gson.Gson

case class Person(firstName: String, lastName: String, age: Int)

object JsonTest {

  def main(args: Array[String]) {
    val spock = Person("Leonard", "Nimoy", 81)
    val gson = new Gson
    println(gson.toJson(spock))
  }
}
