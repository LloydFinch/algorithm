package samestart;

/**
 * 同时开始-信号
 */
public class FireFlag {
    private volatile boolean fired = false;

    /**
     * 等待信号
     */
    public synchronized void waitSingle() throws InterruptedException {
        while (!fired) {
            wait();
        }
    }

    /**
     * 鸣枪开始
     */
    public synchronized void fire() {
        this.fired = true;
        //唤醒其他等待这个条件的线程
        notifyAll();
    }

    public synchronized boolean isFired() {
        return fired;
    }
}
