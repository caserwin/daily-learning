翻译：https://ci.apache.org/projects/flink/flink-docs-release-1.4/dev/datastream_api.html#data-sources

程序通过 StreamExecutionEnvironment.addSource(sourceFunction) 添加数据源。Flink 自带实现好的 source 函数。
此外，可以通过 SourceFunction自定义函数，来读取non-parallel的source。也可以通过实现ParallelSourceFunction接口或继承
RichParallelSourceFunction 自定义函数，来读取parallel的source。<br>

以下是一些在 StreamExecutionEnvironment 中预先定义好的source:<br>
#### 基于文件:<br>
   readTextFile(path): 逐行读取文本文件，即遵循TextInputFormat规范的文件，并将它们作为字符串返回。<br>
   readFile(fileInputFormat, path): 按照指定的文件输入格式读取文件。<br>
   readFile(fileInputFormat, path, watchType, interval, pathFilter, typeInfo): 这是前两个内部调用的方法。它根据给定的fileInputFormat读取路径中的文件。根据提供的watchType，该源可能会定期监视（每间隔ms）新数据的路径。<br>

#### 基于socket:<br>
   socketTextStream(): 从socket读取，元素可以用分隔符分隔。

#### 基于Collection-based:<br>
   fromCollection(Collection): 从Java Java.util.Collection创建一个数据流。集合中的所有元素必须是相同的类型。<br>
   fromCollection(Iterator, Class): 从迭代器创建数据流。该类指定迭代器返回的元素的数据类型。<br>
   fromElements(T ...): 根据给定的对象序列创建数据流。所有对象必须是相同的类型。<br>
   fromParallelCollection(SplittableIterator, Class): 并行创建迭代器中的数据流。该类指定迭代器返回的元素的数据类型。<br>
   generateSequence(from, to): 并行生成给定时间间隔内的数字序列。<br>

#### 自定义：<br>
   addSource(): 附加一个新的源函数。例如要从Kafka读取，您可以使用addSource。这部分内容详见：[connectors](https://ci.apache.org/projects/flink/flink-docs-release-1.4/dev/connectors/index.html)
