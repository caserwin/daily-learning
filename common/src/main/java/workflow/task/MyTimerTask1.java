package workflow.task;

import java.util.TimerTask;

/**
 * Created by yidxue on 2018/7/23
 */
public class MyTimerTask1 extends TimerTask {

    private long start;

    public MyTimerTask1(long start) {
        this.start = start;
    }

    @Override
    public void run() {
        System.out.println("task1 在 " + (System.currentTimeMillis() / 1000 - start / 1000) + " 秒后开始执行！");
        try {
            Thread.sleep(5000);
            System.out.println("task1 在 " + (System.currentTimeMillis() / 1000 - start / 1000) + " 秒后执行完成！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
