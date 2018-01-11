package phoenix

/**
  * User: Erwin
  * Date: 17/12/28 上午9:38
  * Description: 
  */

class RowsBean(var col1: String, var col2: String, var col3: String, var col4: String) {
  def this() {
    this("null", "null", "null", "null")
  }
}
