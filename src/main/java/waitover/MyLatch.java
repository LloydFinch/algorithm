package waitover;

/**
 * 等待结束-信号器
 * java中有专门的CountDownLatch
 * <p>
 * see {@link java.util.concurrent.CountDownLatch}
 */
public class MyLatch {

    private volatile int count = 0;

    public MyLatch(int count) {
        this.count = count;
    }

    /**
     * 等待
     *
     * @throws InterruptedException
     */
    public synchronized void await() throws InterruptedException {
        System.out.println("start await ...");
        while (count > 0) {
            wait();
        }
    }

    /**
     * 改变原子变量并唤醒
     *
     * @throws InterruptedException
     */
    public synchronized void countDown() throws InterruptedException {
        count--;
        System.out.println("count down: " + count);
        if (count <= 0) {
            notify();
        }
    }
}
