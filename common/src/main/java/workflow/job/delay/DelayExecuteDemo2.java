package workflow.job.delay;

import workflow.task.MyTimerTask1;
import workflow.task.MyTimerTask2;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author yidxue
 * 多个job 延时执行示例
 */
public class DelayExecuteDemo2 {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        TimerTask task1 = new MyTimerTask1(start);
        TimerTask task2 = new MyTimerTask2(start);

        Timer timer = new Timer();

        timer.schedule(task1, 1000);
        timer.schedule(task2, 3000);
    }
}
