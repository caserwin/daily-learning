##Spark 性能优化策略：

#### 1. 用reduceByKey、aggregateByKey 代替groupByKey。
reduceByKey会在当前节点（local）中做reduce操作，也就是说，会在shuffle前，尽可能地减小数据量。而groupByKey则不是，它会不做任何处理而直接去shuffle。

#### 2. 不均匀 shuffle
在工作中遇到这样一个问题，需要转换成这样一个非常巨大的RDD A，结构是(countryId, product)，key是国家id，value是商品的具体信息。当时在shuffle的时候，这个hash算法是根据key来选择节点的，但是事实上这个countryId的分布是极其不均匀的，大部分商品都在美国（countryId=1），于是我们通过Ganglia看到，其中一台slave的CPU特别高，计算全部聚集到那一台去了。<br>
找到原因以后，问题解决就容易了，要么避免这个shuffle，要么改进一下key，让它的shuffle能够均匀分布（比如可以拿countryId+商品名称的tuple作key，甚至生成一个随机串）。

#### 3. 先去重，再合并
比如有A、B这样两个规模比较大的RDD，要进行去重合并工作：
A.union(B).distinct()<br>
这样的操作固然正确，但是如果可以先各自去重，再合并，再去重，可以大幅度减小shuffle的开销:
A.distinct().union(B.distinct()).distinct()