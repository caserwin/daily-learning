package workflow.dependency;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author yidxue
 */
public class JobA implements Runnable {

    private CountDownLatch downLatch;
    private String name;

    public JobA(CountDownLatch downLatch, String name) {
        this.downLatch = downLatch;
        this.name = name;
    }

    @Override
    public void run() {
        this.doWork();
        System.out.println(this.name + "运行结束！");
        this.downLatch.countDown();
    }

    private void doWork() {
        System.out.println(this.name + "正在运行!");
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        } catch (InterruptedException ignored) {
        }
    }
}
