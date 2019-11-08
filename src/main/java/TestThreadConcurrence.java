import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadConcurrence {


    public static void main(String[] args) {

//        testThreadExecutor();


        getAvailableProcessors();
    }

    /**
     * 获取最大CPU数量
     */
    private static void getAvailableProcessors() {
        int processors = Runtime.getRuntime().availableProcessors();
        println("processors: " + processors);
    }

    /**
     * 线程池内置方法
     */
    private static void testThreadExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 20, TimeUnit.SECONDS, new LinkedBlockingDeque<>()) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                println("before execute:" + t.getName());
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                println("after execute:" + r.toString());
            }

            @Override
            protected void terminated() {
                super.terminated();
                println("executor terminated");
            }
        };

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            executor.execute(() -> println("this is a runnable" + finalI));
        }
        /**
         * 发送关闭信号，不会终端正在执行的线程
         */
        executor.shutdown();
    }

    public static void println(String msg) {
        System.out.println(msg);
    }
}
