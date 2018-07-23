package workflow.job.timed;

import workflow.task.MyTimerTask1;
import workflow.task.MyTimerTask2;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by yidxue on 2018/7/23
 */
public class AsynchronousTimedExecuteDemo {
    public static void main(String[] args) throws ParseException {
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(2);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date timeDate = dateFormat.parse("2018-07-23 13:49:00");
        long fixRunTime = timeDate.getTime();

        TimerTask task1 = new MyTimerTask1(fixRunTime);
        TimerTask task2 = new MyTimerTask2(fixRunTime);

        // 只能通过延时方式来设定。
        long delayTime = fixRunTime - System.currentTimeMillis();
        newScheduledThreadPool.schedule(task1, delayTime, TimeUnit.MILLISECONDS);
        newScheduledThreadPool.schedule(task2, delayTime, TimeUnit.MILLISECONDS);
    }
}
