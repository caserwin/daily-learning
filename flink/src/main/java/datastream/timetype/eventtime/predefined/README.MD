Flink 中预定义的时间戳/水位线提取器使用示例。包括：

1 AscendingTimestampExtractor
就是不设置延时时间了，把当前已经进来元素的最大事件时间设为水位线。更加准确来说是：
```
@Override
public final Watermark getCurrentWatermark() {
    return new Watermark(currentTimestamp == Long.MIN_VALUE ? Long.MIN_VALUE : currentTimestamp - 1);
}
```

2 BoundedOutOfOrdernessTimestampExtractor
相当于通过参数maxOutOfOrderness，设置了固定延时的时间。有：
```
current_watermark = current_eventtime - maxOutOfOrderness
```
其实和 research.TimeWindowDemo.java 的逻辑是一样的。