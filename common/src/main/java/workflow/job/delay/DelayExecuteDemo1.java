package workflow.job.delay;

import workflow.task.MyTimerTask;
import java.util.Timer;

/**
 * @author yidxue
 * 延时执行示例
 */
public class DelayExecuteDemo1 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Timer timer = new Timer();
        // 延时5秒执行 MyTimerTask()
        timer.schedule(new MyTimerTask(start, 1000, "task1"), 5000);
    }
}
