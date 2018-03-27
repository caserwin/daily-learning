package workflow.timeout;

import java.util.concurrent.Callable;

/**
 * Created by yidxue on 2018/3/27
 */
public class Task implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(4000);
        return "Ready!";
    }
}
