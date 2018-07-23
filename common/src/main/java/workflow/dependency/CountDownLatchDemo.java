package workflow.dependency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yidxue
 * job 等待依赖示例
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        CountDownLatch latch = new CountDownLatch(3);

        JobA w1 = new JobA(latch,"w1");
        JobA w2 = new JobA(latch,"w2");
        JobA w3 = new JobA(latch,"w3");

        JobB boss = new JobB(latch);

        // NOTICE submit 和 execute有什么区别
        executor.execute(w3);
        executor.execute(w2);
        executor.execute(w1);
        executor.execute(boss);

        executor.shutdown();
    }
}