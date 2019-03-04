package samestart;

/**
 * 同时开始-运动员
 */
public class Racer extends Thread {
    private final FireFlag fireFlag;

    public Racer(FireFlag fireFlag) {
        this.fireFlag = fireFlag;
    }

    @Override
    public void run() {
        try {
            synchronized (fireFlag) {
                while (!fireFlag.isFired()) {
                    wait(); //需要用synchronized包括
                    System.out.println("start run: " + Thread.currentThread().getName());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
