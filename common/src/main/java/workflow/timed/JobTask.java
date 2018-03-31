package workflow.timed;

import java.util.TimerTask;

/**
 * Created by yidxue on 2018/3/31
 */
public class JobTask extends TimerTask {
    @Override
    public void run() {
        System.out.println("timestamp: " + System.currentTimeMillis() / 1000);
    }
}
