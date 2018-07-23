package workflow.job.timed;

import workflow.task.JobTask;
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
        Date timeDate = dateFormat.parse("2018-03-31 17:59:00");
        System.out.println("程序执行的时间戳为：" + timeDate.getTime() / 1000);

        Timer timer = new Timer();
//        在指定timeDate执行
        timer.schedule(new JobTask(), timeDate);
    }
}
