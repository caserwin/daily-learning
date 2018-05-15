Map:DataStream → DataStream<br>

FlatMap:DataStream → DataStream<br>

Filter:DataStream → DataStream<br>

ByKey: DataStream → KeyedStream<br>
逻辑上将一个流分成不相交的分区。所有具有相同key的记录都被分配到相同的分区。
在内部，keyBy()是通过Hash分区实现的。

Reduce: KeyedStream → DataStream<br>
在 KeyedStream 流上操作，每次输入两个元素合并，输出一个元素。输入元素和输出元素类型必须一致。

Fold：KeyedStream → DataStream<br>
在 KeyedStream 流上操作，指定一个initial value，进行更新fold value.
每次把最新的元素和fold value进行合并，最后输出一个元素。

Aggregations: KeyedStream → DataStream<br>
在键控数据流上滚动聚合。 min和minBy之间的差别是min返回最小值，而minBy返回该字段中具有最小值的元素（max和maxBy相同）。

Window: KeyedStream → WindowedStream<br>
Windows可以在已分区的KeyedStream上定义。 Windows根据某些特性（例如，在最近5秒内到达的数据）对每个key中的数据进行分组。请参阅窗口以获取窗口的完整说明。

Union: DataStream* → DataStream

Connect: DataStream,DataStream → ConnectedStreams
