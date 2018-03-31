package workflow.timed;

import java.util.Timer;

/**
 * Created by yidxue on 2018/3/28
 * 定时执行示例
 * 交给 Timer 类好了
 */
public class TimedExecuteDemo1 {

    public static void main(String[] args) {
        Timer timer = new Timer();
        System.out.println("start timestamp: " + System.currentTimeMillis() / 1000);
        // 从现在开始 2 秒钟之后启动
        timer.schedule(new JobTask(), 2000);
    }
}
