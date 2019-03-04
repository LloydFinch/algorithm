import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestThread {

    public static void main(String[] args) {
//        testThreadCreate();
//        testSynchronized();

//        testSyncCollections();
        testWaitNotify();
    }

    /**
     * 线程的创建
     */
    public static void testThreadCreate() {

        //继承Thread来创建
        Thread thread = new Thread(() -> {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            println(Thread.currentThread().getId()); //获取id
            println(Thread.currentThread().getName()); //获取名字
            println(Thread.currentThread().getPriority()); //获取权限1-10，默认是5
            println(Thread.currentThread().getState()); //获取状态
            println(Thread.currentThread().isAlive()); //当前线程是否存活，线程被启动后，run方法运行结束前，都返回true
            //当前线程是否是daemon线程，如果程序中只剩下daemon线程，程序就会退出，daemon线程可以理解其他线程的辅助线程，当他
            //辅助的线程退出了，他也就没有存在的意义了，比如垃圾回收线程就是daemon线程，main线程退出的时候，GC线程也会退出
            println(Thread.currentThread().isDaemon());
            println("new thread");
        });
        thread.start();
        println("this is main thread before join===");
        try {
            //"当前线程"最多等待thread 2000ms，如果thread跑完了没大于2000ms，
            //那就紧跟着运行，如果超过了2000ms，那就直接运行，不再等了
            thread.join(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        println("this is main thread after join===");

        //实现Runnable来创建
        Runnable runnable = () -> {
            println(Thread.currentThread().getName());
            println("new runnable");
        };
        Thread thread1 = new Thread(runnable);
        //thread1.start();
    }

    /**
     * 竞态条件/内存可见性
     */
    public static void testRaceCondition() {
        //解决竟态问题
        //1 使用synchronized关键字
        //2 使用显式锁
        //3 使用原子变量


        //内存可见性:一个线程对一个共享变量的修改，另一个线程不一定能马上看到，甚至永远也看不到
        //原因:1修改没有及时同步到内存；2另一个线程根本没有从内存中去取
        //解决: 使用volatile关键字 使用synchronized关键字 使用显式锁同步
    }

    /**
     * synchronized关键字
     */
    public static void testSynchronized() {
        //synchronized可以修饰:实例方法，静态方法，代码块
        //加上synchronized之后，方法内的代码就变成原子操作了

        //1 synchronized实例方法，保护当前对象锁，也就是this锁

        //2 synchronized静态方法，保护的是.class对象
        //这样就会导致：一个执行实例方法，一个执行静态方法，也会出现冲突

        //3 代码块，这时候synchronized保护的就是括号里的对象

        //这里个创建一个共享对象，让所有的thread去访问
        int number = 0;
        Counter counter = new Counter(number);
        List<Thread> allThreads = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            allThreads.add(new Thread(counter));
        }

        for (Thread thread : allThreads) {
            thread.start();
        }

        //synchronized同步的对象可以是任意一个对象，任意一个对象都有一个锁和一个等待队列
        //这样就可以根据不同的竟态，创建不相关的锁


        //deep in synchronized
        //1 可重入性:funcA(),funcB()都是synchronized的，threadA有了锁，在funcA()内部可以直接调用funcB()
        //2 内存可见性:原子操作也不一定是安全的，因为修改的变量还存在"内存可见性"问题，可以加synchronized来保证，
        //  但是如果仅仅是为了保证内存可见性，可以使用更加轻量的volatile关键字
        //3 死锁:尽量避免

    }

    /**
     * 同步容器synchronizedXXXX/并发容器ConcurrentXXXX
     * 通过给容器的方法添加synchronized实现的
     */
    public static void testSyncCollections() {
        //有几种情况下，同步容器就不安全了
        //复合操作:比如map
        //伪同步:因为同步的锁不相同
        //迭代:一个修改一个遍历，java会抛出异常

        /**
         * 迭代的时候改变，会抛出异常
         */
        List<Integer> integers = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 20; i++) {
            integers.add(i);
        }
        for (int i : integers) {
            if (i > 10) {
//                println("remove");
                //这一句会抛出异常
//                integers.remove(i);
            }
        }


        //同步容器效率比较低
        //对比并发容器

        CopyOnWriteArrayList<String> strings = new CopyOnWriteArrayList<>();
        ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<>();
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        ConcurrentSkipListSet<String> set = new ConcurrentSkipListSet<>();
    }

    /**
     * 线程协作wait/notify
     */
    public static void testWaitNotify() {
        WaitThread waitThread = new WaitThread();
        waitThread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        println("fire...");
        waitThread.fire();
        println("in main ,after fire, wait thread state = " + waitThread.getState());
    }

    private static void println(Object object) {
        System.out.println(object);
    }

    /**
     * WaitThread对象，测试wait/notify
     */
    public static class WaitThread extends Thread {
        private volatile boolean fire = false;

        @Override
        public void run() {
            //凡是wait，必须加锁，mmp不知为什么
            try {
                synchronized (this) {
                    println("WaitThread start...,state = " + getState());
                    while (!fire) {
                        println("before wait...,state = " + getState());
                        wait(0);//这个会将当前线程放入条件等待队列，挂在wait函数内部
                        println("after wait...,state = " + getState());
                    }
                }
                //这里有个问题，if和while的区别何在?
                //但是while模式是一个模版，就这么写
//                    if (!fire) {
//                        println("wait...");
//                        wait();
//                    }
                println("fired...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 这一块是在主线程中跑的
         */
        public synchronized void fire() {
            this.fire = true;
            println("before notify...,state = " + getState());//TIMED_WAITING，因为等待条件
            notify(); //线程移出等待队列，准备进入wait函数，但是发现没有锁，进不去，就等着
            println("after notify...,state = " + getState());//BLOCKED，因为等待锁
            //到这里synchronized已经跑完了，释放锁，wait的线程可以进入了，直接进入wait方法中去
        }
    }

    /**
     * Counter对象，测试synchronized关键字
     */
    public static class Counter implements Runnable {

        private volatile int count;
        private static volatile boolean isA = false;
        private int id = 0;

        public Counter(int count) {
            this.count = count;
        }

        public void setId(int id) {
            this.id = id;
        }

        /**
         * 加了synchronized，当前对象同时只有有一个线程进入，所以只有有一个线程
         * 同时访问该方法
         */
        public synchronized void add() {
            //来个休眠玩玩，这样异步的效果才明显
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            println("add: " + count);
        }

        public void sub() {
            count--;
            println("sub: " + count);
        }

        /**
         * 加了synchronized关键字，可以保护当前方法，但是不能保护
         * 其他的没加synchronized关键字的方法
         */
        public synchronized void changeToA() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isA = true;
            println("to A:" + isA);
        }

        /**
         * 不加synchronized关键字，这里也修改了A，所以没什么卵用，
         * 因为另一个线程不需要当前对象锁就可以进来修改A，此时持有当前
         * 对象的线程可能正在修改A，就出错了，需要加上，这样只有持有当前
         * 对象的线程可以访问，而这个线程是顺序执行的，不可能同时访问
         * 上面的方法还在访问下面的方法，所以是安全的
         */
        public synchronized void changeToNOA() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isA = false;
            println("to !A:" + isA);
        }

        /**
         * 虽然加了synchronized，但是由于是静态的，所以已经不安全了
         */
        public static synchronized void changeToNoA1() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isA = false;
            println("to !A:" + isA);
        }

        @Override
        public void run() {
//            add();
            this.id = new Random().nextInt(2);
            if (id % 2 == 0) {
                changeToA();
            } else {
//                changeToNOA();
                changeToNoA1();
            }
        }
    }
}
