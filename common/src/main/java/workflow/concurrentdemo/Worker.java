package workflow.concurrentdemo;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author yidxue
 */
public class Worker implements Runnable {

    private CountDownLatch downLatch;
    private String name;

    public Worker(CountDownLatch downLatch, String name) {
        this.downLatch = downLatch;
        this.name = name;
    }

    @Override
    public void run() {
        this.doWork();
        System.out.println(this.name + "活干完了！");
        this.downLatch.countDown();
    }

    private void doWork() {
        System.out.println(this.name + "正在干活!");
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        } catch (InterruptedException ignored) {
        }
    }
}
