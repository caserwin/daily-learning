package workflow.job.period;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by yidxue on 2018/3/31
 */
public class PeriodExecutorDemo2 {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        System.out.println("start timestamp: " + System.currentTimeMillis() / 1000);

        executor.scheduleAtFixedRate(() -> {
            System.out.println(System.currentTimeMillis() / 1000 + "\tit is job1 normal print!");
        }, 6, 10, TimeUnit.SECONDS);

        executor.scheduleAtFixedRate(() -> {
            System.out.println(System.currentTimeMillis() / 1000 + "\tit is job2 normal print!");
        }, 8, 8, TimeUnit.SECONDS);
    }
}
