package sql.udaf.demo4

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._

class EventTypeUDAF extends UserDefinedAggregateFunction {
  // Input Data Type Schema
  def inputSchema: StructType = StructType(Array(StructField("str", StringType)))

  // Intermediate Schema
  def bufferSchema: StructType = StructType(Array(StructField("flag", IntegerType)))

  // Returned Data Type .
  def dataType: DataType = IntegerType

  // Self-explaining
  def deterministic = true

  // This function is called whenever key changes
  def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = 0
  }

  // Iterate over each entry of a group
  def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    if ("downloadend".equals(input.getString(0).toLowerCase())){
      buffer(0) = 1
    }
  }

  // Merge two partial aggregates
  def merge(buffer: MutableAggregationBuffer, input: Row): Unit = {
    if (input.getInt(0)==1){
      buffer(0) = 1
    }
  }

  // Called after all the entries are exhausted.
  def evaluate(buffer: Row): Int = {
    buffer.getInt(0)
  }
}
