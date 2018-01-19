package core.json

/**
  * User: Erwin
  * Date: 17/12/5 上午9:26
  * Description: 
  */

import java.util
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import scala.collection.JavaConverters._

case class Person(firstName: String, lastName: String, age: Int)

object JsonTest {

  def main(args: Array[String]) {
    val spock = Person("Leonard", "Nimoy", 81)
    val gson = new Gson
    println(gson.toJson(spock))


    val context = "{\"firstName\":\"Leonard\",\"lastName\":\"Nimoy\",\"age\":81}"
    val mapType = new TypeToken[util.HashMap[String, String]] {}.getType
    val myMap = gson.fromJson[Map[String, String]](context, mapType)

    myMap.keys.foreach{ i =>
      print( "Key = " + i )
      println(" Value = " + myMap(i) )}
  }
}
