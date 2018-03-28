# 任务调度机制

## 1. 应用场景
有以下应用场景：
1. job 运行等待机制，就是一个job 要依赖其他job执行完成。
2. job 超时退出机制，就是一个job 运行时间超过设定的时间，则直接退出job。
3. job 异步延时执行，就是设置一个job 在另一个job执行后的指定时间内执行。
4. job 异步周期执行，几个job可以并发地周期性执行。
5. job 定时执行、周期执行、固定延时执行

问题1可基于concurrent.CountDownLatch 和 concurrent.ExecutorService 实现。<br>
问题2可基于concurrent.ScheduledExecutorService或concurrent.ExecutorService 实现<br>
问题3可基于concurrent.ScheduledExecutorService或concurrent.ExecutorService 实现<br>
问题4可基于concurrent.ScheduledExecutorService或concurrent.ExecutorService 实现<br>
问题5基于Timer类和TimeTask类<br>


## 2. Timer和 TimeTask 类说明   
### Timer、TimeTask类业务场景
job 定时执行、周期执行、固定延时执行<br> 多个job之间的延时执行，只能是同步的。
### Timer、TimeTask类代码解析
参考：http://wiki.jikexueyuan.com/project/java-enhancement/java-add1.html
### Timer、TimeTask类的缺陷
Timer 类是项目中常用的定时器，比如每隔一段时间清理项目中的一些垃圾文件，每个一段时间进行数据清洗。
然而Timer是存在一些缺陷的，因为Timer在执行定时任务时只会创建一个线程，多任务之间只能串行执行。
即一个任务执行完，才能执行另一个任务。

   

## 3. concurrent包相关说明

### 3.1 CountDownLatch
#### 业务场景
#### 代码解析

### 3.2 ExecutorService 和 ScheduledExecutorService
#### 业务场景
#### 代码解析

ExecutorService接口有一个非常重要的子接口： ScheduledExecutorService，从它的名字，
我们就能看出此service是为了支持时间可控的任务执行而设计，其中包括：异步固定延迟执行，周期性执行；
不过他还不支持制定特定date执行，这个工作可以交给Timer来做(稍后参看timer讲解)

异步延时执行
ScheduledExecutorService.schedule(Runnable command,long delay, TimeUnit unit)<br>
ScheduledExecutorService.schedule(Callable<V> callable,long delay, TimeUnit unit)<br>
异步周期执行
ScheduledExecutorService.scheduleAtFixedRate(Runnable command,long initialDelay,long period,TimeUnit unit)<br>


## 参考
http://shift-alt-ctrl.iteye.com/blog/1841088
https://stackoverflow.com/questions/19456313/simple-timeout-in-java
https://stackoverflow.com/questions/2758612/executorservice-that-interrupts-tasks-after-a-timeout
https://stackoverflow.com/questions/2275443/how-to-timeout-a-thread

https://blog.csdn.net/lmj623565791/article/details/27109467