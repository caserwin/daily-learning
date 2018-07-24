package workflow.job.timed;

import workflow.task.MyTimerTask;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yidxue on 2018/7/23
 */
public class TimedExecuteDemo2 {

    public static void main(String[] args) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date timeDate = dateFormat.parse("2018-07-23 23:54:30");

        long start = timeDate.getTime();

        if (start < System.currentTimeMillis()) {
            System.out.println("定期执行时间必须大于当前系统时间！");
            return;
        }

        TimerTask task1 = new MyTimerTask(start, 5000, "task1");
        TimerTask task2 = new MyTimerTask(start, 1000, "task2");

        Timer timer = new Timer();

        timer.schedule(task1, timeDate);
        timer.schedule(task2, timeDate);
    }
}
