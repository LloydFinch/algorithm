import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 异步任务
 */
public class TestExecutor {

    public static void main(String[] args) {
//        try {
//            test1();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        testThreadPoolExecutor();

        testTimer();
    }

    private static void test1() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(() -> {
            int time = new Random().nextInt(100);
            Thread.sleep(time);
            println("call in " + Thread.currentThread().getName());
            return time;
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Integer result = future.get();
        println("the result is: " + result);
        executor.execute(() -> println("execute runnable"));
        executor.shutdown(); //不再接收任务，不会结束已经提交的
        executor.shutdownNow();//不再接收任务，并且会结束已经提交的

        //线程池
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(2, 2, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(8));
        threadPoolExecutor.prestartAllCoreThreads(); //预先创建所有核心线程
        threadPoolExecutor.prestartCoreThread();//预先创建一个核心线程
        threadPoolExecutor.allowCoreThreadTimeOut(true);//使得核心线程可以被回收，这个可以有
        threadPoolExecutor.execute(() -> {
            println("execute in threadPool");
        });

        Executors.newSingleThreadExecutor();//core:1 max:1 alive:0
        Executors.newFixedThreadPool(4); //core:4 max:4 alive:0
        Executors.newCachedThreadPool(); //core:0 max:Integer.MAX_VALUE alive:60s
    }

    /**
     * 线程池参数问题
     */
    public static void testThreadPoolExecutor() {
        int core = 2;
        int max = 10;
        int alive = 0;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        //非阻塞的queue不能用于线程池
//        ConcurrentLinkedQueue<Runnable> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        LinkedBlockingDeque<Runnable> linkedBlockingDeque = new LinkedBlockingDeque<>(); //无界的queue，max无效，最多创建core个线程，可以视为max = core
        ArrayBlockingQueue<Runnable> arrayBlockingQueue = new ArrayBlockingQueue<>(3); //有界的queue，上述参数都有效
        SynchronousQueue<Runnable> synchronousQueue = new SynchronousQueue<>(); //使用这个，会无限创建线程，可以视为max = Integer.MAX_VALUE
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(core, max, alive, timeUnit, synchronousQueue);
        threadPoolExecutor.setRejectedExecutionHandler((r, executor) -> println("被拒绝了"));
        for (int i = 1; i <= 10; i++) {
            println("thread:" + i);
            threadPoolExecutor.execute(() -> {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        println(threadPoolExecutor.getPoolSize() + "-" + threadPoolExecutor.getCorePoolSize());
        println("----------------" + threadPoolExecutor.getActiveCount() + "-" + threadPoolExecutor.getMaximumPoolSize() + "-" + threadPoolExecutor.getLargestPoolSize() + "-" + threadPoolExecutor.getTaskCount());
    }

    private static void testTimer() {
        //两种定时器的构造方式
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        ScheduledExecutorService executorService1 = new ScheduledThreadPoolExecutor(1);
        //每隔一秒打印一次时间
        //按照时间打印，根据当前时间决定，打印一次
        executorService.scheduleWithFixedDelay(() -> println("time = " + new Date()), 0, 1, TimeUnit.SECONDS);
        //按照次数打印，会补全次数，打印两次哦
        executorService.scheduleAtFixedRate(() -> println("time2 = " + new Date()), 0, 1, TimeUnit.SECONDS);
        try {
            Thread.sleep(1000); //1秒后关闭计时器
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();//取消计时任务
    }

    public static void println(Object o) {
        System.out.println(o);
    }
}
