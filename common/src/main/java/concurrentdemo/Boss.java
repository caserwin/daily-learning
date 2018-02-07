package concurrentdemo;

import java.util.concurrent.CountDownLatch;

/**
 * @author yidxue
 */
public class Boss implements Runnable {

    private CountDownLatch downLatch;

    public Boss(CountDownLatch downLatch) {
        this.downLatch = downLatch;
    }

    @Override
    public void run() {
        System.out.println("老板正在等所有的工人干完活......");
        try {
            this.downLatch.await();
        } catch (InterruptedException ignored) {
        }
        System.out.println("工人活都干完了，老板开始检查了！");
    }
}
