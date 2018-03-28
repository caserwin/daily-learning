package workflow.delay;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author yidxue
 * 延时执行示例
 */
public class DelayExecuteDemo1 {
    static class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("程序结束时间："+System.currentTimeMillis()/1000);
        }
    }
    public static void main(String[] args) {
        Timer timer = new Timer();
        System.out.println("程序开始时间："+System.currentTimeMillis()/1000);
        // 延时5秒执行 MyTimerTask()
        timer.schedule(new MyTimerTask(), 5000);
    }
}
