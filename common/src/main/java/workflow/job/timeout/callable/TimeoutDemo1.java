package workflow.job.timeout.callable;

import java.util.concurrent.*;

/**
 * @author yidxue
 * 超时退出示例
 * code from: https://stackoverflow.com/questions/2275443/how-to-timeout-a-thread
 */
public class TimeoutDemo1 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new TaskCall());
        try {
            System.out.println(future.get(5, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            // mayInterruptIfRunning设成false话，不允许在线程运行时中断，设成true的话就直接终端。
            future.cancel(true);
            System.out.println("it is time out!");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdownNow();
    }
}

