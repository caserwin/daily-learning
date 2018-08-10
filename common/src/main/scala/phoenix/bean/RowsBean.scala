package phoenix.bean

/**
  * User: Erwin
  * Date: 17/12/28 上午9:38
  * Description: 
  */

class RowsBean(var id: String, var name: String, var city: String, var discount: String) {
  def this() {
    this("null", "null", "null", "null")
  }
}
