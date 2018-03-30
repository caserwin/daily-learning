package workflow.timeout.runable;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by yidxue on 2018/3/27
 * 参考：https://stackoverflow.com/questions/2758612/executorservice-that-interrupts-tasks-after-a-timeout
 * 超时退出示例
 * 思路如下：
 * 1. 先起一个job，这个job就是你实际要监控是否会timeout的job。
 * 2. 再起一个新的job可以测试你之前的那个job是否会在指定的时间内timeout，如果超时了，则把之前的那个job退出。
 *
 * NOTICE: 整个用户线程的时间就是延迟的时间。其实我的本意是为被监控的job（也就是TaskRun线程）写一个守护线程。
 * NOTICE: 也就是说，我是希望监控线程是一个守护线程，但是现在监控线程变成了用户线程。这其实不是我想要的。
 */
public class TimeoutDemo2 {

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
         Future<?> future = executor.submit(new TaskRun());
        // 再起一个job，延迟5秒执行kill之前的job。
        executor.schedule(() -> {
            future.cancel(true);
            System.out.println("it is time out!");
        }, 100, TimeUnit.SECONDS);

        // NOTICE: shutdownNow() 和 shutdown() 有什么区别？？
        executor.shutdown();
    }
}
