package workflow.job.timed;

import workflow.task.MyTimerTask;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

/**
 * Created by yidxue on 2018/3/31
 */
public class TimedExecuteDemo1 {

    public static void main(String[] args) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date timeDate = dateFormat.parse("2018-07-23 23:52:30");
        long start = timeDate.getTime();

        if (start < System.currentTimeMillis()) {
            System.out.println("定期执行时间必须大于当前系统时间！");
            return;
        }

        Timer timer = new Timer();
        // 在指定timeDate执行
        timer.schedule(new MyTimerTask(start,1000,"task1"), timeDate);
    }
}
