这里需要注意的是 FlinkIntervalJoinDemo 类：
#### 参数1
```
long delay = 7000L;
long positive = 5000L;
long negative = -5000L;
```
输出：
```
(a,1,hangzhou,1000000050000,1000000052000)
(a,2,hangzhou,1000000054000,1000000052000)
(b,6,beijing,1000000108000,1000000104000)
```

#### 参数2
```
long delay = 6999L;
long positive = 5000L;
long negative = -5000L;
```
输出：
```
(a,1,hangzhou,1000000050000,1000000052000)
(a,2,hangzhou,1000000054000,1000000052000)
```