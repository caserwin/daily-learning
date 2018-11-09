package sql.myudaf.demo4

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._

class ConcatStrUDAF extends UserDefinedAggregateFunction{

  // Input Data Type Schema
  def inputSchema: StructType = StructType(Array(StructField("str", StringType)))

  // Intermediate Schema
  def bufferSchema: StructType = StructType(Array(StructField("cancatStr", StringType)))

  // Returned Data Type .
  def dataType: DataType = StringType

  // Self-explaining
  def deterministic = true

  // This function is called whenever key changes
  def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = ""
  }

  // Iterate over each entry of a group
  def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0) = buffer.getString(0)  + input.getString(0)+ " "
  }

  // Merge two partial aggregates
  def merge(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0) = buffer.getString(0) + input.getString(0)+ " "
  }

  // Called after all the entries are exhausted.
  def evaluate(buffer: Row): String = {
    buffer.getString(0).substring(0,buffer.getString(0).length-2)
  }
}
