package asynctask;

import java.util.concurrent.Callable;

/**
 * 异步任务的执行器，在这里创建线程，并等待执行结果
 */
public class MyExecutor {

    public <V> MyFuture<V> execute(final Callable<V> task) {
        final Object lock = new Object();
        final ExecuteThread<V> thread = new ExecuteThread<>(task, lock);
        thread.start();

        MyFuture<V> future = () -> {
            try {
                synchronized (lock) {
                    while (!thread.isDone()) {
                        lock.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (thread.getException() != null) {
                throw thread.getException();
            }
            return thread.getResult();
        };

        return future;
    }

}
