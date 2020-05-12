
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTT {

    public static void main(String[] args) {
        while (true) {
            System.out.println("while...");
        }
    }


    private static void ThreadPoolAugu() {


        ThreadFactory threadFactory = r -> {
            Thread thread = new Thread(r);
            thread.setName(String.valueOf(System.currentTimeMillis()));
            thread.setDaemon(true);
            return thread;
        };


        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 3, 60, TimeUnit.SECONDS, new LinkedBlockingDeque(), (r, executor1) -> {
            System.out.println("current executor can only run no more than 3 task!");
        });
    }
}
