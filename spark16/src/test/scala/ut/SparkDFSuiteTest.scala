package JMTBatchAnalysis.transformers.df


import common.SparkFunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

/**
  * Created by dubin on 12/12/2017.
  */
@RunWith(classOf[JUnitRunner])
class EventLogTransformerSuiteTest extends SparkFunSuite {

  test("event log transformer test") {

    val inputFileName = "EventLogTransformerInput.csv"
    val expectFileName = "EventLogTransformerOutput.csv"
    val inputDF = getDFFromResourceCSV(inputFileName)
    val outputDF = EventLogTransformer.transform(sparkContext, inputDF)
    val expectDF = getDFFromResourceCSV(expectFileName)
    assert(outputDF.asDataFrameEquals(expectDF))
  }
}