import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class TestConcurrence {

    public static void main(String[] args) {
//        testAtomicDemo();
        testAtomicXXX();
    }

    public static void testAtomicDemo() {

        int number = 0;
        int count = 10000;
        AtomicInteger atomicInteger = new AtomicInteger(number);
        Thread[] threads = new Thread[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new Visit("" + i);
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
//                thread.start();
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        print("run finished, number is: " + Visit.count);
        print("run finished, atomicInteger is: " + Visit.atomicInteger.get());
    }

    public static class Visit extends Thread {
        public volatile static int count;
        //使用原子变量，不需要加锁
        public static AtomicInteger atomicInteger = new AtomicInteger(0);

        public Visit(@NotNull String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            incr();
        }

        private void incr() {
//            //需要加锁才能保证顺序
//            synchronized (Visit.class) {
//                count++;
//                print(count);
//            }
//
            print("atomicInteger: " + atomicInteger.incrementAndGet());

            print("-----count---: " + Thread.currentThread().getName());
            //test2 join的问题
//            print("count: " + (++count));
            synchronized (Visit.class) {
                print("count: " + (++count));
            }
        }
    }

    /**
     * AtomicInteger
     * AtomicBoolean
     */
    public static void testAtomicXXX() {
        int init = 10;
        AtomicInteger atomicInteger = new AtomicInteger(init);

        int number = atomicInteger.get();
        atomicInteger.getAndIncrement();
        atomicInteger.getAndDecrement();
        int delta = 1;
        atomicInteger.getAndAdd(delta);


        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        print("before : " + atomicBoolean.get());
        print("after1 : " + atomicBoolean.getAndSet(true));
        print("after2 : " + atomicBoolean.getAndSet(true));

        AtomicStampedReference<Boolean> atomicStampedReference = new AtomicStampedReference<>(false, 10);
    }

    /**
     * 显式锁
     */
    private static void testLock() {

    }

    private static void print(Object o) {
        if (o != null) {
            System.out.println(o.toString());
        }
    }

    /**
     * 模仿的原子变量
     * 使用synchronized只允许同时有一个线程读取
     * 缺点:需要获取锁/等待锁，还会切线程
     */
    public static class Counter {

        //不需要加volatile，因为不存在一个线程读，一个线程写的情况
        private volatile int count;

        public synchronized void inc() {
            count++;
        }

        public synchronized int getCount() {
            return count;
        }
    }

    /**
     * 使用AtomicInteger实现一个锁
     */
    public static class MyLock {

        private boolean lock = false;

        //使用这个玩意儿来实现同步锁
        private AtomicBoolean aBoolean = new AtomicBoolean(false);

        /**
         * 需要锁定当前线程
         */
        public void lock() {
            lock = true;//当前线程跑到这里后，另一个线程跑了unlock

            //只要不是由未锁定->锁定，就一直在这里
            while (!aBoolean.compareAndSet(false, true)) {
                Thread.yield();
            }
        }

        /**
         * 解锁，当前线程可以跑
         * 重新去抢占cpu
         */
        public void unlock() {
            lock = false;
            // 锁定->解锁
            aBoolean.compareAndSet(true, false);
        }
    }
}
