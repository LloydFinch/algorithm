package samestart;

/**
 * 同时开始-信号
 */
public class FireFlag {
    private volatile boolean fired = false;

    public synchronized void fire() {
        this.fired = true;
        //唤醒其他等待这个条件的线程
        notifyAll();
    }

    public synchronized boolean isFired() {
        return fired;
    }
}
