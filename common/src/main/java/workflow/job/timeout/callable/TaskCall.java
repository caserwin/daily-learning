package workflow.job.timeout.callable;

import java.util.concurrent.Callable;

/**
 * Created by yidxue on 2018/3/27
 */
public class TaskCall implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(4000);
        System.out.println("it is normal print!");
        return "it is normal return!";
    }
}
