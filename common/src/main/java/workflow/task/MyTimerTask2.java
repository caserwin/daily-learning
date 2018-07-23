package workflow.task;

import java.util.TimerTask;

/**
 * Created by yidxue on 2018/7/23
 */
public class MyTimerTask2 extends TimerTask {
    private long start;

    public MyTimerTask2(long start) {
        this.start = start;
    }

    @Override
    public void run() {
        System.out.println("task2 在 " + (System.currentTimeMillis() / 1000 - start / 1000) + " 秒后开始执行！");
    }
}
