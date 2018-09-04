## 说明
主要关于窗口边界和以及延时数据处理的调研。首先我们要知道一个结论，窗口触发的条件：
```
1、watermark时间 >= window_end_time
2、在[window_start_time,window_end_time)中有数据存在
```
参考：https://blog.csdn.net/lmalds/article/details/52704170

## timeWindow 边界计算的源码

在 org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows 类中有个start变量，记录窗口开始时间。
```
@Override
public Collection<TimeWindow> assignWindows(Object element, long timestamp, WindowAssignerContext context) {
    if (timestamp > Long.MIN_VALUE) {
        // Long.MIN_VALUE is currently assigned when no timestamp is present
        long start = TimeWindow.getWindowStartWithOffset(timestamp, offset, size);
        return Collections.singletonList(new TimeWindow(start, start + size));
    } else {
        throw new RuntimeException("Record has Long.MIN_VALUE timestamp (= no timestamp marker). " +
                "Is the time characteristic set to 'ProcessingTime', or did you forget to call " +
                "'DataStream.assignTimestampsAndWatermarks(...)'?");
    }
}
```
start的计算逻辑需要接着看 getWindowStartWithOffset()的源码。这里timestamp是数据的 eventtime。code 如下：

```
/**
 * Method to get the window start for a timestamp.
 *
 * @param timestamp epoch millisecond to get the window start.
 * @param offset The offset which window start would be shifted by.
 * @param windowSize The size of the generated windows.
 * @return window start
 */
public static long getWindowStartWithOffset(long timestamp, long offset, long windowSize) {
    return timestamp - (timestamp - offset + windowSize) % windowSize;
}
```

## timeWindow 问题汇总
### 关于数据分组问题
其实也就是要先确定windows的时间边界：包括 start time 和 end time。 <br>
对于 timeWindow(Time.seconds(14)) 这样的窗口。其实就是
```
offset = 0
windowSize = 14000
```
确定窗口 start 边界的Code如下，end 边界就是 start+ windowSize。
```
public class BoundaryForTimeWindowTest {
    public static void main(String[] args) {

        // 注意是毫秒为单位
        long windowsize = 14000L;
        // 注意是毫秒为单位
        long offset = 0L;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        long a1 = 1000000050000L;
        long a2 = 1000000054000L;
        long a3 = 1000000079900L;
        long a4 = 1000000115000L;
        long b5 = 1000000100000L;
        long b6 = 1000000109000L;

        System.out.println(a1 + " -> " + format.format(a1) + "\t所属窗口的起始时间是: " + getWindowStartWithOffset(a1, offset, windowsize) + " -> " + format.format(getWindowStartWithOffset(a1, offset, windowsize)));
        System.out.println(a2 + " -> " + format.format(a2) + "\t所属窗口的起始时间是: " + getWindowStartWithOffset(a2, offset, windowsize) + " -> " + format.format(getWindowStartWithOffset(a2, offset, windowsize)));
        System.out.println(a3 + " -> " + format.format(a3) + "\t所属窗口的起始时间是: " + getWindowStartWithOffset(a3, offset, windowsize) + " -> " + format.format(getWindowStartWithOffset(a3, offset, windowsize)));
        System.out.println(a4 + " -> " + format.format(a4) + "\t所属窗口的起始时间是: " + getWindowStartWithOffset(a4, offset, windowsize) + " -> " + format.format(getWindowStartWithOffset(a4, offset, windowsize)));
        System.out.println(b5 + " -> " + format.format(b5) + "\t所属窗口的起始时间是: " + getWindowStartWithOffset(b5, offset, windowsize) + " -> " + format.format(getWindowStartWithOffset(b5, offset, windowsize)));
        System.out.println(b6 + " -> " + format.format(b6) + "\t所属窗口的起始时间是: " + getWindowStartWithOffset(b6, offset, windowsize) + " -> " + format.format(getWindowStartWithOffset(b6, offset, windowsize)));

    }

    private static long getWindowStartWithOffset(long timestamp, long offset, long windowSize) {
        return timestamp - (timestamp - offset + windowSize) % windowSize;
    }
}
```
输出：
```
1000000050000 -> 2001-09-09 09:47:30.000	所属窗口的起始时间是: 1000000050000 -> 2001-09-09 09:47:30.000
1000000054000 -> 2001-09-09 09:47:34.000	所属窗口的起始时间是: 1000000050000 -> 2001-09-09 09:47:30.000
1000000079900 -> 2001-09-09 09:47:59.900	所属窗口的起始时间是: 1000000078000 -> 2001-09-09 09:47:58.000
1000000115000 -> 2001-09-09 09:48:35.000	所属窗口的起始时间是: 1000000106000 -> 2001-09-09 09:48:26.000
1000000100000 -> 2001-09-09 09:48:20.000	所属窗口的起始时间是: 1000000092000 -> 2001-09-09 09:48:12.000
1000000109000 -> 2001-09-09 09:48:29.000	所属窗口的起始时间是: 1000000106000 -> 2001-09-09 09:48:26.000
```
当窗口长度为14秒的时候，对于元素 1000000109000 所属的窗口就是 [1000000106000,1000000180000)


### 关于延时数据
先吐个槽，研究这个这个非常麻烦，不过现在我还是有自己的理解。

#### 情况1：元素在水位线以下，但是windows没被触发计算
##### 参数：
```
    long delay = 5000L;
    int windowSize = 10;
```
##### 输入：
```
    Tuple3[] elements = new Tuple3[]{
                    Tuple3.of("a", "1", 1000000050000L),
                    Tuple3.of("a", "2", 1000000054000L),
                    Tuple3.of("a", "3", 1000000079900L),
                    Tuple3.of("a", "4", 1000000120000L),
                    Tuple3.of("b", "5", 1000000111000L),
                    Tuple3.of("b", "6", 1000000089000L)
                };
```
##### 输出：
```
1 -> 1000000050000 -> 2001-09-09 09:47:30.000
2 -> 1000000054000 -> 2001-09-09 09:47:34.000
3 -> 1000000079900 -> 2001-09-09 09:47:59.900
(a,12,1)
4 -> 1000000120000 -> 2001-09-09 09:48:40.000
(a,3,1000000079900)
5 -> 1000000111000 -> 2001-09-09 09:48:31.000
6 -> 1000000089000 -> 2001-09-09 09:48:09.000
(b,5,1000000111000)
(a,4,1000000120000)
```
##### 说明：
当地4条记录(4 -> 1000000120000 -> 2001-09-09 09:48:40.000) 进来的时候，watermark = 2001-09-09 09:48:35.000。
但是第5条记录(5 -> 1000000111000 -> 2001-09-09 09:48:31.000) 并没有被触发计算，因为第5条记录所属的窗口是[2001-09-09 09:48:30.000, 2001-09-09 09:48:40.000)
也就是说 watermark < windows end time，所以窗口没被触发计算。所以第5条记录进来还是能被计算的。
但是对于第6条数据（6 -> 1000000089000 -> 2001-09-09 09:48:09.000）所属的窗口范围是[2001-09-09 09:48:00.000,2001-09-09 09:48:10.000)
也就是说当前的 watermark > windows end time。所以第6条数据所属的窗口就永远不会被触发计算了，因此第6条数据也就丢失了。

#### 情况2：元素在水位线以下，但是windows已经无法被触发计算了
其实就是进一步验证上面的结论。
##### 参数：
```
    long delay = 5000L;
    int windowSize = 10;
```
##### 输入：
```
      Tuple3[] elements = new Tuple3[]{
            Tuple3.of("a", "1", 1000000050000L),
            Tuple3.of("a", "2", 1000000054000L),
            Tuple3.of("a", "3", 1000000079900L),
            Tuple3.of("a", "4", 1000000120000L),
            Tuple3.of("b", "5", 1000000100001L),
            Tuple3.of("b", "6", 1000000109000L)
        };
```
##### 输出：
```
1 -> 1000000050000 -> 2001-09-09 09:47:30.000
2 -> 1000000054000 -> 2001-09-09 09:47:34.000
3 -> 1000000079900 -> 2001-09-09 09:47:59.900
(a,12,1)
4 -> 1000000120000 -> 2001-09-09 09:48:40.000
(a,3,1000000079900)
5 -> 1000000100001 -> 2001-09-09 09:48:20.001
6 -> 1000000109000 -> 2001-09-09 09:48:29.000
(a,4,1000000120000)
```
##### 说明：
现在第4个元素和第5个元素其实都是属于 [2001-09-09 09:48:20.000,2001-09-09 09:48:30.000) 窗口。
但是这个窗口的 windows end time < watermark，所以这个窗口已经无法被触发计算了。
其实也就是这个窗口创建时，已经 windows end time < watermark ，相当于第4条和第5条数据都丢失了。

这里我们再改一下输入，把最大的delay时间设置成 5.1s。窗口长度设为 15秒。
参数:
```
    long delay = 5100L;
    int windowSize = 15;
```

输入:
```
 Tuple3[] elements = new Tuple3[]{
                Tuple3.of("a", "1", 1000000050000L),
                Tuple3.of("a", "2", 1000000054000L),
                Tuple3.of("a", "3", 1000000079900L),
                Tuple3.of("a", "4", 1000000115000L),
                Tuple3.of("b", "5", 1000000100000L),
                Tuple3.of("b", "6", 1000000108000L)
            };
```
输出：
```
1 -> 1000000050000 -> 2001-09-09 09:47:30.000
2 -> 1000000054000 -> 2001-09-09 09:47:34.000
3 -> 1000000079900 -> 2001-09-09 09:47:59.900
(a,12,1)
4 -> 1000000115000 -> 2001-09-09 09:48:35.000
(a,3,1000000079900)
5 -> 1000000100000 -> 2001-09-09 09:48:20.000
6 -> 1000000108000 -> 2001-09-09 09:48:28.000
(b,56,1)
(a,4,1000000115000)
```

为啥会这样呢？？因为第4条数据(4 -> 1000000115000 -> 2001-09-09 09:48:35.000) 进来的时候，watermark = 2001-09-09 09:48:29.900。
但是第5条和第6条的所属的时间窗口是 [2001-09-09 09:48:15.000, 2001-09-09 09:48:30.000) 。也就是说第5条和第6条数据进来的时候，watermark < windows end time。
所以这种情况下，就算数据的 eventtime < watermark 但是数据还是被保留了，没有丢失。

## timeWindow 使用总结
1. 如果当前数据的 EventTime 在 WaterMark 之上，也就是 EventTime> WaterMark。因为我们知道数据所属窗口的 WindowEndTime，一定是大于 EventTime 的。
这是候，我们有 WindowEndTime > EventTime > WaterMark。所以这种情况下数据是一定不会丢失的。
2. 如果当前数据的 EventTime 在 WaterMark 之下，也就是 WaterMark > EventTime。这时候要分两种情况：<br>
    2.1 如果该数据所属窗口的 WindowEndTime > WaterMark，则表示窗口还没被触发，即 WindowEndTime > WaterMark > EventTime，这种情况数据也是不会丢失的。<br>
    2.1 如果该数据所属窗口的 WaterMark > WindowEndTime，则表示窗口已经无法被触发，即 WaterMark > WindowEndTime > EventTime，这种情况数据也就丢失了。

