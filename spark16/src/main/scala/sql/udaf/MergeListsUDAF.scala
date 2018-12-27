package sql.udaf

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._

import scala.collection.mutable

/**
  * Created by yidxue on 2018/12/27
  */
class MergeListsUDAF extends UserDefinedAggregateFunction {

  override def inputSchema: StructType = StructType(Seq(StructField("a", ArrayType(IntegerType))))

  override def bufferSchema: StructType = inputSchema

  override def dataType: DataType = ArrayType(IntegerType)

  override def deterministic: Boolean = true

  override def initialize(buffer: MutableAggregationBuffer): Unit = buffer.update(0, mutable.Seq[Int]())

  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    val existing = buffer.getAs[mutable.Seq[Int]](0)
    val newList = input.getAs[mutable.Seq[Int]](0)
    //    val result = (existing ++ newList).distinct
    val result = existing ++ newList
    buffer.update(0, result)
  }

  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = update(buffer1, buffer2)

  override def evaluate(buffer: Row): Any = buffer.getAs[mutable.Seq[Int]](0)
}
