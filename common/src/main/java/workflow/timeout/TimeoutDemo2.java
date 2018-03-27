package workflow.timeout;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by yidxue on 2018/3/27
 * 参考：https://stackoverflow.com/questions/2758612/executorservice-that-interrupts-tasks-after-a-timeout
 * 思路如下：
 * 1. 先起一个job，这个job就是你实际要监控是否会timeout的job。
 * 2. 再起一个新的job可以测试你之前的那个job是否会在指定的时间内timeout，如果超时了，则把之前的那个job退出。
 */
public class TimeoutDemo2 {

    public static void main(String[] args) {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        final Future<String> handler = executor.submit(new Task());

        // 再起一个job，延迟5秒执行kill之前的job。
        executor.schedule(() -> {
            handler.cancel(true);
        }, 5, TimeUnit.SECONDS);

        // NOTICE: shutdownNow() 和 shutdown() 有什么区别？？
        executor.shutdown();
    }
}
