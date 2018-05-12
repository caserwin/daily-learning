[Flink DataSet API Programming Guide](https://ci.apache.org/projects/flink/flink-docs-release-1.4/dev/batch/) <br>
[DataSet Transformations（非常推荐）](https://ci.apache.org/projects/flink/flink-docs-release-1.4/dev/batch/dataset_transformations.html)<br>

map<br>
对于集合中的每个元素进行一对一映射。

flatMap<br>
对于原集合中的每个元素，进行一对多的映射。

mapPartition<br>
在一个function中一次调用一个分区的数据。

filter<br>
对集合中的每个元素进行判断，保留符合条件的结果。

project<br>
保留或者重新排列一个集合中的指定index的字段。

distinct<br>
对Tuple整体进行去重，也可以对Tuple中某个指定字段进行去重

union<br>
合并两个数据类型一致的 DataSet。

rebalance<br>
均匀分区，消除数据倾斜。均匀分多少个区，依赖 env.setParallelism() 参数

reduce<br>
通过重复地将两个元素合成一个元素，来把一组元素合成一个元素，通常用于groupby()之后，也可以用于一个完整的数据集。<br>

groupby<br>
groupBy 是flink中的分组函数，后面要跟一个sum, reduce, first 等聚合函数。<br>可以通过index 指定分组的key，也可以通过KeySelector方式指定key.<br>

aggregate<br>
在flink中常用的聚合函数包括min,max,sum等。但是这些聚合函数Tuple类型的DataSet,而且只能通过字段的index(下标索引)的方式进行的聚合。


groupReduce<br>
用于groupBy()之后，与Reduce的区别在于GroupReduce的每次执行可以获取一个分组的数据，对这个分组数据进行聚合操作。而reduce的每次执行只获得分组中的两个数据，对两个数据进行聚合，更像Hive以及Spark中的UDAF概念。