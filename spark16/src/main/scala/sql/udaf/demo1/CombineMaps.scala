package sql.udaf.demo1

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._

/** *
  * UDAF combining maps, overriding any duplicate key with "latest" value
  *
  *  https://gist.github.com/tzachz/c976a1080b6379ef861c142c16f1364a
  *
  * @param keyType   DataType of Map key
  * @param valueType DataType of Value key
  * @param merge     function to merge values of identical keys
  * @tparam K key type
  * @tparam V value type
  */
class CombineMaps[K, V](keyType: DataType, valueType: DataType, merge: (V, V) => V) extends UserDefinedAggregateFunction {
  override def inputSchema: StructType = new StructType().add("map", dataType)

  override def bufferSchema: StructType = inputSchema

  override def dataType: DataType = MapType(keyType, valueType)

  override def deterministic: Boolean = true

  override def initialize(buffer: MutableAggregationBuffer): Unit = buffer.update(0, Map[K, V]())

  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    val map1 = buffer.getAs[Map[K, V]](0)
    val map2 = input.getAs[Map[K, V]](0)
    val result = map1 ++ map2.map { case (k, v) => k -> map1.get(k).map(merge(v, _)).getOrElse(v) }
    buffer.update(0, result)
  }

  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = update(buffer1, buffer2)

  override def evaluate(buffer: Row): Any = buffer.getAs[Map[String, Int]](0)
}
