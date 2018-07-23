package workflow.job.delay;

import workflow.task.MyTimerTask;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author yidxue
 * 多个job之间的异步执行示例
 */
public class AsynchronousDelayExecuteDemo1 {

    public static void main(String[] args) {
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(2);
        long start = System.currentTimeMillis();

        TimerTask task1 = new MyTimerTask(start, 3000,"task1");
        TimerTask task2 = new MyTimerTask(start,2000,"task2");

        newScheduledThreadPool.schedule(task1, 1000, TimeUnit.MILLISECONDS);
        newScheduledThreadPool.schedule(task2, 3000, TimeUnit.MILLISECONDS);
    }
}