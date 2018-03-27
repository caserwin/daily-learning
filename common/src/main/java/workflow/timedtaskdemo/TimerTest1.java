package workflow.timedtaskdemo;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author yidxue
 */
public class TimerTest1 {
    static class MyTimerTask1 extends TimerTask {
        @Override
        public void run() {
            System.out.println("爆炸！！！");
            System.out.println("程序结束时间："+System.currentTimeMillis()/1000);
        }
    }
    public static void main(String[] args) {
        Timer timer = new Timer();
        System.out.println("程序开始时间："+System.currentTimeMillis()/1000);
        // 两秒后启动任务
        timer.schedule(new MyTimerTask1(), 2000);
    }
}
