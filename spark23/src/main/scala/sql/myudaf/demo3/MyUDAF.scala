package sql.myudaf.demo3

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._

/**
  * http://www.voidcn.com/article/p-zflfwoxr-ys.html
  */
class MyUDAF extends UserDefinedAggregateFunction {
  /** * 指定具体的输入数据的类型 * 自段名称随意：Users can choose names to identify the input arguments - 这里可以是“name”，或者其他任意串 */
  override def inputSchema: StructType = StructType(Array(StructField("name", StringType, nullable = true)))

  /** * 在进行聚合操作的时候所要处理的数据的中间结果类型 */
  override def bufferSchema: StructType = StructType(Array(StructField("count", IntegerType, nullable = true)))

  /** * 返回类型 */
  override def dataType: DataType = IntegerType

  /** * whether given the same input, * always return the same output * true: yes */
  override def deterministic: Boolean = true

  /** * Initializes the given aggregation buffer */
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = 0
  }

  /** * 在进行聚合的时候，每当有新的值进来，对分组后的聚合如何进行计算 * 本地的聚合操作，相当于Hadoop MapReduce模型中的Combiner */
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0) = buffer.getInt(0) + 1
  }

  /** * 最后在分布式节点进行local reduce完成后需要进行全局级别的merge操作 */
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getInt(0) + buffer2.getInt(0)
  }

  /** * 返回UDAF最后的计算结果 */
  override def evaluate(buffer: Row): Any = buffer.getInt(0)
}
