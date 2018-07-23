package workflow.job.delay;

import workflow.task.MyTimerTask;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author yidxue
 * 多个job 延时执行示例
 */
public class DelayExecuteDemo2 {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        TimerTask task1 = new MyTimerTask(start, 3000, "task1");
        TimerTask task2 = new MyTimerTask(start, 2000, "task2");

        Timer timer = new Timer();

        timer.schedule(task1, 1000);
        timer.schedule(task2, 3000);
    }
}
