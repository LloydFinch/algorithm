import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 并发工具类
 */
public class TestConcurrentTools {

    public static void main(String[] args) {
        testSemaphore();
//        testCountDownLatch();
//        testCyclicBarrier();
//        testThreadLocal();
    }

    /**
     * ReentrantReadWriteLock
     */
    private void testReadWriteLock() {
        MySafetyCache<String, String> cache = new MySafetyCache<>();
        cache.set("hello", "java");
        cache.get("hello");
    }

    /**
     * Semaphore 不可重入
     * 重入需要消耗一个凭证(acquire())
     */
    private static void testSemaphore() {
        try {
            //给10个许可证
            Semaphore semaphore = new Semaphore(0);

            //可以由另一个线程release
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                semaphore.release();
            }).start();

            semaphore.acquire();
            println("acquire1");
//
//            semaphore.acquire(); //这里不release就走不进来，因为不是可重入的
//            println("acquire2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * CountDownLatch
     * 同时开始
     */
    private static void testCountDownLatch() {
        int num = 10; //10个参与者在赛跑
        int delay = 5; //倒计时5秒
        CountDownLatch countDownLatch = new CountDownLatch(delay);
        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();//等着
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                println(Thread.currentThread().getName() + " start");
            }).start();
        }

        //开始倒计时delay秒
        for (int i = delay; i > 0; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            println("倒计时 " + i + " 秒...");
            countDownLatch.countDown();
        }
    }

    /**
     * CyclicBarrier
     * 等待结束
     */
    private static void testCyclicBarrier() {
        int parties = 6; //5个参与者
        println(Thread.currentThread().getName() + "主线程");
        Runnable runnable = () -> {
            println(Thread.currentThread().getName() + "最后到达");
        };//最后一个参与者做的事
        CyclicBarrier cyclicBarrier = new CyclicBarrier(parties, runnable);
        for (int i = 0; i < parties - 1; i++) {
            new TT(cyclicBarrier).start();
        }

        //这个例外的thread里面少了一个await，其他的所有thread都会一直等待他的await
        new Thread(() -> {
            try {
                Thread.sleep(new Random().nextInt(1000) + 100);
                //这个表示已经到达一个集合点A
                println(Thread.currentThread().getName() + "到达C");
                cyclicBarrier.await(); //通知自己已经到达
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
        cyclicBarrier.reset();//重置
    }

    /**
     * ThreadLocal
     */
    public static void testThreadLocal() {
        //线程独立的，每个线程只能获取到在自己本省中设置的值，不受其他线程影响
        ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> {
            //用来赋初始值
            return 300;
        });
        println(threadLocal.get());
        //当前在main线程中，赋值100
        threadLocal.set(100);
        println("main-thread, set: " + 100);
        new Thread(() -> {
            println("new-thread, get: " + threadLocal.get()); //在新线程中取值
            threadLocal.set(200);
            println("new-thread, set: " + 200);
            println("new thread, get: " + threadLocal.get()); //在自身线程中赋值后再取值
        }).start();

        try {
            //休眠1s，确保新线程已经赋值为200
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        println("main-thread, get: " + threadLocal.get());

        threadLocal.remove();//移除在当前线程赋予的值，那么就只剩原来的初始值，就是300
        println("main-thread, after remove: " + threadLocal.get());


        //ThreadLocal随机数,减少资源冲突
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        threadLocalRandom.nextInt();

        //CAS的底层就是根据这玩意实现的
//        Unsafe unsafe = Unsafe.getUnsafe();
    }

    /**
     * 用来测试CyclicBarrier
     */
    public static class TT extends Thread {
        public CyclicBarrier cyclicBarrier;

        public TT(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(new Random().nextInt(1000) + 100);
                //这个表示已经到达一个集合点A
                println(Thread.currentThread().getName() + "到达A");
                cyclicBarrier.await(); //通知自己已经到达

                //再来一个集合点B
                Thread.sleep(new Random().nextInt(1000) + 100);
                println(Thread.currentThread().getName() + "到达B");
                cyclicBarrier.await(); //通知自己已经到达

            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用ReentrantReadWriteLock
     */
    public static class MySafetyCache<K, V> {
        private Map<K, V> map = new HashMap<>();
        private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

        /**
         * 写
         *
         * @param key
         * @param value
         */
        public void set(K key, V value) {
            writeLock.lock();
            try {
                map.put(key, value);
            } finally {
                writeLock.unlock();
            }
        }

        /**
         * 读
         *
         * @param key
         * @return
         */
        public V get(K key) {
            readLock.lock();
            try {
                return map.get(key);
            } finally {
                readLock.unlock();
            }
        }

        /**
         * 另类的写
         */
        public void clear() {
            writeLock.lock();
            try {
                map.clear();
            } finally {
                writeLock.unlock();
            }
        }
    }

    private static void println(Object o) {
        System.out.println(o);
    }
}
