package core.implicitdemo

/**
  * Created by yidxue on 2018/7/15
  */
object ScalaImplicitDemo3 {

  class Man(val name: String)

  class SuperMan(val name: String) {
    def fly(): Unit = {
      println(name + " can fly")
    }
  }

  def main(args: Array[String]): Unit = {
    implicit def man2SuperMan(man: Man): SuperMan = new SuperMan(man.name)

    val man = new Man("erwin")
    man.fly()
  }
}
