package workflow.job.timeout.runable;

/**
 * Created by yidxue on 2018/3/28
 */
public class TaskRun implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(4000);
            System.out.println("it is normal print!");
        } catch (InterruptedException e) {
            System.err.println("it is time out!");
        }
    }
}
