package workflow.job.period;

import workflow.task.JobTask;
import java.util.Timer;

/**
 * Created by yidxue on 2018/3/28
 */
public class PeriodExecutorDemo1 {
    public static void main(String[] args){
        Timer timer = new Timer();
        System.out.println("start timestamp: " + System.currentTimeMillis() / 1000);
        // 从现在开始 2 秒钟之后启动
        timer.schedule(new JobTask(), 3000,2000);
    }
}
