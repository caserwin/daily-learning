package workflow.job.delay;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author yidxue
 * 多个job 延时执行示例
 */
public class DelayExecuteDemo2 {
    private static long start;

    public static void main(String[] args) throws Exception {

        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task1 invoked ! " + (System.currentTimeMillis() - start));
                try {
                    Thread.sleep(5000);
                    System.out.println("task1 is done in " + (System.currentTimeMillis() - start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task2 invoked ! " + (System.currentTimeMillis() - start));
            }
        };

        Timer timer = new Timer();
        start = System.currentTimeMillis();
        timer.schedule(task1, 1000);
        timer.schedule(task2, 3000);
    }
}
