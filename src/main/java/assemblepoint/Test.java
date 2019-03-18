package assemblepoint;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 集合点：跟同时到达没什么区别
 */
public class Test {

    public static Timer timer;

    public static void main(String[] args) {
        //测试一下Timer
        System.out.println("ui thread:" + Thread.currentThread().getName());

        timer = new Timer(true);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("main:" + Thread.currentThread().getName());
            }
        };
        timer.schedule(timerTask, 0, 1000);

        new Thread("hello") {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("new thread:" + Thread.currentThread().getName());
                }
            }
        }.start();
        test();
    }

    public static void test() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("test:" + Thread.currentThread().getName());
            }
        };
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        timer = new Timer(true);
        timer.schedule(timerTask, 0, 1000);
    }
}
