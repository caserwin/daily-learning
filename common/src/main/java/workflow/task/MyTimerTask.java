package workflow.task;

import java.util.TimerTask;

/**
 * Created by yidxue on 2018/7/23
 */
public class MyTimerTask extends TimerTask {

    private long start;
    private long runTime;
    private String taskName;

    public MyTimerTask(long start, long runTime, String taskName) {
        this.start = start;
        this.runTime = runTime;
        this.taskName = taskName;
    }

    @Override
    public void run() {
        System.out.println(taskName + " 在 " + (System.currentTimeMillis() / 1000 - start / 1000) + " 秒后开始执行！");
        try {
            Thread.sleep(runTime);
            System.out.println(taskName + " 在 " + (System.currentTimeMillis() / 1000 - start / 1000) + " 秒后执行完成！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
