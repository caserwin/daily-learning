package workflow.dependency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
            // 超时等待机制
            boolean is = this.downLatch.await(60, TimeUnit.SECONDS);
            if (!is) {
                System.out.println("等待超时！！退出程序");
                System.exit(0);
            }
        } catch (InterruptedException ignored) {
        }
        System.out.println("工人活都干完了，老板开始检查了！");
    }
}
