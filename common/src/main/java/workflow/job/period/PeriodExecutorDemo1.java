package workflow.job.period;

import workflow.task.MyTimerTask2;
import java.util.Timer;

/**
 * Created by yidxue on 2018/3/28
 */
public class PeriodExecutorDemo1 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        // 从现在开始 2 秒钟之后启动
        timer.schedule(new MyTimerTask2(System.currentTimeMillis()), 3000, 2000);
    }
}
