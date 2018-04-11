package common

import java.io.File

import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{BeforeAndAfterAll, FunSuite}

/**
  * Spark Fun Suite for Spark 1.6x
  */
class SparkFunSuite extends FunSuite with BeforeAndAfterAll {

  var sparkContext: SparkContext = _
  var sqlContext: SQLContext = _

  implicit def DataFrameToRichDataFrame(df: DataFrame) = new RichDataFrame(df)

  override def beforeAll(): Unit = {
    val sparkConf = new SparkConf().setMaster("local").setAppName("SparkFunSuiteConf")
    sparkContext = new SparkContext(sparkConf)
    sqlContext = new SQLContext(sparkContext)
    super.beforeAll()
  }

  override protected def afterAll(): Unit = {
    if (sparkContext != null) {
      sparkContext.stop()
    }
    super.afterAll()
  }

  protected final def getTestResourceFile(file: String): File = {
    new File(getClass.getClassLoader.getResource(file).getFile)
  }

  protected final def getTestResourcePath(file: String): String = {
    getTestResourceFile(file).getCanonicalPath
  }

  protected final def getDFFromResourceCSV(filename: String): DataFrame = {
    val path = getClass.getClassLoader.getResource(filename).getPath
    sqlContext.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .load(path)
  }

}


class RichDataFrame(df: DataFrame) {

  val dataFrame: DataFrame = df

  def asDataFrameEquals(that: DataFrame): Boolean = {

    if (!countEquals(that)) {
      return false
    }
    if (!columnsEquals(that)) {
      return false
    }

    true
  }

  /**
    * Compare the two DataFrame count
    * @param that
    * @return
    */
  private def countEquals(that: DataFrame): Boolean = {
    dataFrame.count().equals(that.count())
  }

  /**
    * Compare the two DataFrame columns
    * @param that
    * @return
    */
  private def columnsEquals(that: DataFrame): Boolean = {
    dataFrame.show()
    that.show()
    val thisColumns = dataFrame.columns
    thisColumns.sortBy(x => x)
    val thatColumns = that.columns
    thatColumns.sortBy(x => x)
    if (thisColumns.length != thatColumns.length) {
      return false
    }

    for (i <- thisColumns.indices) {
//      println(thisColumns(i), thatColumns(i))
      if (!thisColumns(i).equals(thatColumns(i))) {
        return false
      }
    }
    true
  }

}
