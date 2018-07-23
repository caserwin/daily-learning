package workflow.job.delay;

import workflow.task.JobTask;

import java.util.Timer;

/**
 * @author yidxue
 * 延时执行示例
 */
public class DelayExecuteDemo1 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        System.out.println("程序开始时间：" + System.currentTimeMillis() / 1000);
        // 延时5秒执行 MyTimerTask()
        timer.schedule(new JobTask(), 5000);
    }
}
