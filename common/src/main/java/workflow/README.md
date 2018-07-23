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
job 定时执行、周期执行、固定延时执行，但是都只能是同步的。<br> 

Timer 的优点在于简单易用，但由于所有任务都是由同一个线程来调度，因此所有任务都是串行执行的，同一时间只能有一个任务在执行，
前一个任务的延迟或异常都将会影响到之后的任务。
例如我们指定每隔 1000 毫秒执行一次任务，但是可能某个任务执行花了 5000 毫秒，因此导致后续的任务并不能按时启动执行。

### Timer、TimeTask类代码解析
参考：
http://wiki.jikexueyuan.com/project/java-enhancement/java-add1.html


Timer类 schedule 方法解析。

public void schedule(TimerTask task, long delay)                       // 从现在起过delay毫秒执行一次。<br> 
public void schedule(TimerTask task, Date time)                        // 在指定时间执行一次。<br> 
public void schedule(TimerTask task, long delay, long period)          // 从现在起过delay毫秒以后，每隔period。<br> 
public void schedule(TimerTask task, Date firstTime, long period)      // 从firstTime时刻开始，每隔period毫秒执行一次。<br>  

同时也重载了scheduleAtFixedRate方法，scheduleAtFixedRate方法与schedule相同，只不过他们的侧重点不同，区别后面分析。<br> 
scheduleAtFixedRate(TimerTask task, Date firstTime, long period)：安排指定的任务在指定的时间开始进行重复的固定速率执行。<br> 
scheduleAtFixedRate(TimerTask task, long delay, long period)：安排指定的任务在指定的延迟后开始进行重复的固定速率执行。<br> 

关于 scheduleAtFixedRate 不解释。

### Timer、TimeTask类的缺陷
Timer 类是项目中常用的定时器，比如每隔一段时间清理项目中的一些垃圾文件，每个一段时间进行数据清洗。
然而Timer是存在一些缺陷的，因为Timer在执行定时任务时只会创建一个线程，多任务之间只能串行执行。
即一个任务执行完，才能执行另一个任务。

   

## 3. concurrent包相关说明

### 3.1 CountDownLatch
#### 业务场景
用于job之间的依赖等待。
#### 代码解析
见workflow/dependency的demo。

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



## ScheduledExecutorService

这个借口一共定义了4个方法。
ScheduledExecutorService.schedule(Runnable command,long delay, TimeUnit unit)   // 异步延时执行<br>
ScheduledExecutorService.schedule(Callable<V> callable,long delay, TimeUnit unit)   // 异步延时执行<br>
ScheduledExecutorService.scheduleAtFixedRate(Runnable command,long initialDelay,long period,TimeUnit unit) // 异步周期执行<br>
ScheduledExecutorService.scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit);

scheduleAtFixedRate 和 scheduleWithFixedDelay 有啥区别？？



## 参考
http://shift-alt-ctrl.iteye.com/blog/1841088
https://stackoverflow.com/questions/19456313/simple-timeout-in-java
https://stackoverflow.com/questions/2758612/executorservice-that-interrupts-tasks-after-a-timeout
https://stackoverflow.com/questions/2275443/how-to-timeout-a-thread

https://blog.csdn.net/lmj623565791/article/details/27109467