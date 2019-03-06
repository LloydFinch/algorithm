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
//            fireFlag.waitSingle();
            synchronized (fireFlag) {
                while (!fireFlag.isFired()) {
                    fireFlag.wait();
                }
            }
            System.out.println(System.currentTimeMillis() + "start run: " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
