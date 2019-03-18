import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

public class TestConcurrence {

    public static void main(String[] args) {
        testAtomicDemo();
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
     */
    public static void testAtomicInteger() {
        int init = 10;
        AtomicInteger atomicInteger = new AtomicInteger(init);

        int number = atomicInteger.get();
        atomicInteger.getAndIncrement();
        atomicInteger.getAndDecrement();
        int delta = 1;
        atomicInteger.getAndAdd(delta);
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
}
