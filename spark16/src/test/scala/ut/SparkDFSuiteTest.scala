package ut

import common.SparkFunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
  */
@RunWith(classOf[JUnitRunner])
class SparkDFSuiteTest extends SparkFunSuite {

  test("transformer test") {
    val inputFileName = "SparkDFInput.csv"
    val expectFileName = "SparkDFOutput.csv"
    val inputDF = getDFFromResourceCSV(inputFileName)
    val outputDF = DataFrameUTDemo.transformDF(sqlContext, inputDF)
    val expectDF = getDFFromResourceCSV(expectFileName)
    assert(outputDF.asDataFrameEquals(expectDF))
  }
}