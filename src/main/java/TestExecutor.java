import java.util.Random;
import java.util.concurrent.*;

/**
 * 异步任务
 */
public class TestExecutor {

    public static void main(String[] args) {
        try {
            test1();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private static void testTimer() {

    }

    public static void println(Object o) {
        System.out.println(o);
    }
}
