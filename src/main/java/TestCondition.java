import org.jetbrains.annotations.NotNull;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestCondition {

    public static void main(String[] args) {
        testCondition();
    }

    /**
     * 显式条件
     */
    public static void testCondition() {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Condition condition1 = lock.newCondition();
        println(condition == condition1);


        Object lock1 = new Object();
        WaitThread waitThread1 = new WaitThread("A", lock1);
        WaitThread waitThread2 = new WaitThread("B", lock1);
        waitThread1.setmLock(lock);
        waitThread2.setmLock(lock);

        waitThread1.start();
        waitThread2.start();
    }


    public static class WaitThread extends Thread {

        private final Object lock;
        private Lock mLock;
        private Condition condition;

        WaitThread(@NotNull String name, Object lock) {
            super(name);
            this.lock = lock;
        }

        public void setmLock(Lock mLock) {
            this.mLock = mLock;
            this.condition = mLock.newCondition();
        }

        @Override
        public void run() {
            println(getName() + " run...");
            //enter();
            enterByCondition();
        }

        synchronized void enter() {
            println(getName() + " enter");
            synchronized (lock) {
                try {
                    println(getName() + " start wait");
                    wait();
                    println(getName() + " wait over");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        void enterByCondition() {
            println(getName() + "-----enterByCondition");
            mLock.lock();
//            mLock.lockInterruptibly(); //当前线程还未interrupt的状态下才lock
            try {
                println(getName() + " -----condition start wait");
                condition.await();
                println(getName() + " -----condition wait over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mLock.unlock();
            }
        }
    }


    public static void println(Object o) {
        System.out.println(o);
    }
}
