package z_concurrence;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test {

    public static void main(String[] args) {
//        stopThreadByInterrupt();

//        testThreadWaitInThread();

//        testThreadGroup();

        testDemon();
    }

    /**
     * 通过中断停止线程
     */
    private static void stopThreadByInterrupt() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    //添加对中断的检测，来退出线程
                    if (isInterrupted()) {
                        println("thread is interrupt");
                        break;
                    }
                    Thread.yield();
                }
            }
        };
        thread.start();

        try {
            Thread.sleep(1000);
            println("before interrupt, thread state: " + thread.getState());
            thread.interrupt();
            Thread.sleep(2000); //保证中断完成，才打印下面的语句
            println("after interrupt, thread state: " + thread.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
            //中断抛出异常会清除中断标记位
        }
    }


    /**
     * 在Thread2上调用Thread1.wait()，停止的是Thread2
     */
    private static void testThreadWaitInThread() {
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                println("in thread t1...");
            }
        });
        t1.start();


        Thread t2 = new Thread(() -> {
            try {
                synchronized (t1) {
                    println("in thread2 before thread1 wait");
                    t1.wait(); //这里的t1就是一个object
                    println("in thread2 after thread1 wait");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t2.start();

        try {
            Thread.sleep(10000);
            synchronized (t1) {
                t1.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 线程组
     */
    private static void testThreadGroup() {
        ThreadGroup threadGroup = new ThreadGroup("ThreadGroup");
        Thread t1 = new Thread(threadGroup, new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            println("t1");
        }), "t1");
        Thread t2 = new Thread(threadGroup, new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            println("t2");
        }), "t2");

        t1.start();
        t2.start();
        println(threadGroup.activeCount());
        threadGroup.list();
    }

    /**
     * 守护线程
     */
    private static void testDemon() {
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(1000);
                    println("I am daemon thread");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true); //设为守护线程
        thread.start();

        try {
            Thread.sleep(10000); //让主线程10s后再退出
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 住重点
     */
    private static void warning() {
        Integer integer = 0;
        synchronized (integer) {
            integer++; //这里有风险，因为integer不在-128-127之内会创建新对象，加锁的对象就变了
            println(integer);
        }
    }

    /**
     * JDK并发包
     */
    private static void jdkTools() throws Exception {
        /**
         * 读写锁
         * 信号量
         * 倒计时门闩
         * 循环栅栏
         * ThreadLocal
         */

        //读写锁:持有两个锁，可以分别对读/写进行控制
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
        readLock.lock();
        //执行读操作，不影响写
        readLock.unlock();
        writeLock.lock();
        //执行写操作，不影响读
        writeLock.unlock();


        //信号量:持有n个凭证，每次进行一些操作都需要消耗凭证
        int permits = 8;
        Semaphore semaphore = new Semaphore(permits);
        semaphore.acquire(1);//请求获取n个凭证，如果获取到就继续向下跑，否则会让当前线程wait在这里
        boolean b = semaphore.tryAcquire(1);//尝试获取n个凭证，不管是否获取到都立刻返回
        semaphore.release(1); //释放n个凭证
        semaphore.release();//释放一个凭证

        //倒计时门闩:
        int count = 3; //倒计时总共3次
        CountDownLatch countDownLatch = new CountDownLatch(count);
        countDownLatch.await();//先等着，会卡在这里
        countDownLatch.countDown(); //开始计时:2
        countDownLatch.countDown(); //时间:1
        countDownLatch.countDown(); //时间:0，卡在await()的那些玩意会可以继续向下跑了

        //循环栅栏:
        int parties = 8; //8个参与者
        //指定最后一个到达终点的人要做的事情，会在最后一个到达终点的人的线程中跑
        Runnable barrierAction = () -> {
            println("last man");
        };
        CyclicBarrier cyclicBarrier = new CyclicBarrier(parties, barrierAction);
        //通知自己已经到达，8个参与者，必须有8个await()，否则会一直等待下去，直到等齐8个await()才会继续向下跑
        cyclicBarrier.await();
        cyclicBarrier.reset(); //重置

        //线程本地变量:线程独立的，在一个线程中的修改，另一个线程看不到
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        Thread t1 = new Thread(() -> threadLocal.set("t1"));

        Thread t2 = new Thread(() -> {
            String string = threadLocal.get(); //是null
            println(string);
            threadLocal.remove(); //删除不了，只能删除本线程修改的变量
        });
    }

    /**
     * 重入锁，响应中断，可以进行限时等待，可以保证公平性
     */
    private static void testReentrantLcok() throws Exception {
        ReentrantLock reentrantLock = new ReentrantLock(); //默认是不公平的
        ReentrantLock reentrantLock1 = new ReentrantLock(true);//传递参数表示需要一个公平锁
        reentrantLock.lock(); //普通锁方法，不响应中断
        reentrantLock.lock(); //可重入，所以能进来
        //do something

        reentrantLock.unlock(); //必须释放相同的次数
        reentrantLock.unlock();

        reentrantLock.lockInterruptibly();//响应中断，表示可以被中断终止

        boolean b = reentrantLock.tryLock();//尝试获取锁，避免死锁的好方法之一
        reentrantLock.tryLock(10 * 1000, TimeUnit.SECONDS);//尝试获取锁，限时等待，最多等待10s


        /**
         * Condition条件，可以响应中断，使用相关方法之前需要获取对应的锁
         */
        Condition condition = reentrantLock.newCondition();
        condition.await(); //响应中断 object.wait();
        condition.awaitUninterruptibly();//不响应中断
        condition.signal(); //object.notify();
        condition.signalAll(); //object.notifyAll();
    }

    private static void println(Object o) {
        System.out.println(o);
    }
}
