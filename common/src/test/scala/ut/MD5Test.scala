package ut

import core.ScalaHashMD5Demo
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * Created by yidxue on 2018/4/23
  */
@RunWith(classOf[JUnitRunner])
class MD5Test extends FunSuite{

  test("MD5 test") {
    assertEquals(ScalaHashMD5Demo.md5("Hello"), "8b1a9953c4611296a827abf8c47804d7")
  }
}
