package workflow.dependency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author yidxue
 */
public class JobB implements Runnable {

    private CountDownLatch downLatch;
    public JobB(CountDownLatch downLatch) {
        this.downLatch = downLatch;
    }

    @Override
    public void run() {
        System.out.println("JobB 正在等所有的 JobA 完成......");
        try {
            // 超时等待机制
            boolean is = this.downLatch.await(60, TimeUnit.SECONDS);
            if (!is) {
                System.out.println("等待超时！！退出程序");
                System.exit(0);
            }
        } catch (InterruptedException ignored) {
        }
        System.out.println("JobA执行完成，JobB 开始运行！");
    }
}
