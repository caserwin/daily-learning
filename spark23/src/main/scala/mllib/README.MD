## 数据类型
### 向量
Spark Mllib中向量两种形式：dense密集型和sparse稀疏型。一个dense类型的向量背后其实就是一个数组，而sparse向量背后则是两个并行数组：索引数组和值数组。
比如向量(1.0, 0.0, 3.0)既可以用密集型向量表示为[1.0, 0.0, 3.0]，也可以用稀疏型向量表示为(3, [0,2],[1.0,3.0])，其中3是数组的大小。

```
import org.apache.spark.ml.linalg.{Vector,Vectors}
import org.apache.spark.sql.SparkSession

object DataTypes {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("tf-idf").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    // 创建dense vector
    val dv: Vector = Vectors.dense(1.0, 0.0, 3.0)
    // 创建sparse vector
    val sv1: Vector = Vectors.sparse(3, Array(0,2), Array(1.0,3.0))
    val sv2: Vector = Vectors.sparse(3, Seq((0, 1.0), (2,3.0)))
  }
}
```

其中sparse vector有两种创建方式，第一种是传入三个参数：向量大小、索引数组、索引数组对应的值数组；第二种方式是传入两个参数：向量大小、由索引和值组成的键值对数组。

### Labeled Point
labeled point其实内部也是一个vector，可能是dense也可能是sparse，不过多了一个标签列。在ML里面，labeled point通常用于有监督算法。
这个label是double类型的，这样既可以用于回归算法，也可以用于分类。在二分类中，Label不是0就是1；在多分类中label可能从0开始，1，2，3 ...

```
// Create a labeled point with a positive label and a dense feature vector.
val pos = LabeledPoint(1.0, Vectors.dense(1.0, 0.0, 3.0))

// Create a labeled point with a negative label and a sparse feature vector.
val neg = LabeledPoint(0.0, Vectors.sparse(3, Array(0, 2), Array(1.0, 3.0)))
```

一般在准备训练集数据的时候，数据都是稀疏型的。MMLib支持在SVM和Liner线性回归中直接读取训练数据，但是需要满足下面的格式：

```
label index1:value1 index2:value2 ...
```
比如:
```
val examples: RDD[LabeledPoint] = MLUtils.loadLibSVMFile(sc, "data/mllib/sample_libsvm_data.txt")
```

## 特征工程
特征转化一般是：
StringIndexer、VectorAssembler、OneHotEncoderEstimator

1. https://blog.csdn.net/liulingyuan6/article/details/53397780
2. https://blog.csdn.net/liulingyuan6/article/details/53410832

## 资料
1. [知乎专栏:叶子子学习笔记](https://zhuanlan.zhihu.com/liulingyuan)
