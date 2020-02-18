package thread;

public class TestWaitLock {


    public static void main(String[] args) {
        TestWaitLock testWaitLock = new TestWaitLock();

        testWaitLock.testWaitSynchronizedLock();
    }


    /**
     * 原因：wait()会释放锁，但是wait()需被synchronized包括，synchronized块结束了才会释放锁，所以到底是哪里释放的锁
     * 这里需要验证
     * <p>
     * 验证结果:wait()会阻塞后续执行，也就是卡在wait()的地方，synchronized也就不存在结束(因为调不到，出不去),所以wait()是
     * 释放锁的(而且是有效的)
     */
    public void testWaitSynchronizedLock() {
        Object lock = new Object();
        Thread t1 = new Thread() {
            @Override
            public void run() {
                /**
                 * 请求锁
                 */
                synchronized (lock) {
                    System.out.println("in t1...");

                    try {
                        /**
                         * 释放锁
                         */
                        lock.wait();

                        /**
                         * 这里进行等待，保证在wait()后，synchronized结束前有一段时间
                         */
                        System.out.println("t1 start sleep");
                        Thread.sleep(1000);
                        System.out.println("t1 end sleep");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    /**
                     * synchronized结束，也会释放锁
                     */
                    System.out.println("t1 synchronized end");
                }
            }
        };


        Thread t2 = new Thread() {
            @Override
            public void run() {
                /**
                 * 请求锁
                 */
                synchronized (lock) {
                    System.out.println("in t2...");
                    lock.notify();
                }
            }
        };

        t1.start();
        t2.start();
    }
}
