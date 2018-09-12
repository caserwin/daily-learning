对于 FlinkTimeWindowsDemo 这个比较重要，需要记录。设置延迟4秒。

## 1 数据源1：
```
    DataStream<Tuple3<String, String, Long>> source = env.fromElements(
        Tuple3.of("a", "1", 1000000050000L),
        Tuple3.of("a", "2", 1000000054000L),
        Tuple3.of("a", "3", 1000000079900L),
        Tuple3.of("a", "4", 1000000115000L),
        Tuple3.of("b", "4", 1000000100000L),
        Tuple3.of("b", "5", 1000000108000L)
    );
```
输出
```
watermark -> -9223372036854775808
watermark -> 1000000046000
watermark -> 1000000050000
watermark -> 1000000075900
watermark -> 1000000111000
watermark -> 1000000111000
(a,12,1)
(a,3,1000000079900)
(b,56,1)
(a,4,1000000115000)
```

## 2 数据源2：
```
    DataStream<Tuple3<String, String, Long>> source = env.addSource(new DataSource()).name("Demo Source");
```
输出
```
watermark -> -9223372036854775808
watermark -> 1000000046000
watermark -> 1000000050000
(a,12,1)
watermark -> 1000000075900
(a,3,1000000079900)
watermark -> 1000000111000
watermark -> 1000000111000
(a,4,1000000115000)
```

## 3 数据源3：
通过 socket 方式读取数据
```
env.socketTextStream("localhost", 9994).name("Demo Source").map(new sourceMap());
```
输入：
```
a,1,1000000050000
a,2,1000000054000
a,3,1000000079900
a,4,1000000115000
b,5,1000000100000
b,6,1000000108000
c,7,1000000208000
c,8,1000000308000
```
#### 3.1 参数1：
delay = 4 秒的输出如下：
```
watermark -> -9223372036854775808
watermark -> 1000000046000
watermark -> 1000000050000
(a,12,1)
watermark -> 1000000075900
(a,3,1000000079900)
watermark -> 1000000111000
watermark -> 1000000111000
watermark -> 1000000111000
(a,4,1000000115000)
watermark -> 1000000204000
(c,7,1000000208000)
```

#### 3.2 参数2：
delay = 6 秒的输出如下：
```
watermark -> -9223372036854775808
watermark -> 1000000044000
watermark -> 1000000048000
(a,12,1)
watermark -> 1000000073900
(a,3,1000000079900)
watermark -> 1000000109000
watermark -> 1000000109000
watermark -> 1000000109000
(b,56,1)
(a,4,1000000115000)
watermark -> 1000000202000
(c,7,1000000208000)
```

