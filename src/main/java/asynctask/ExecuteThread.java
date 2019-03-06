package asynctask;

import java.util.concurrent.Callable;

/**
 * 异步任务的执行子线程
 */
public class ExecuteThread<V> extends Thread {
    private V result; //执行结果
    private Exception exception = null; //异常信息
    private boolean done = false; //是否执行完毕
    private Callable<V> task; //需要异步执行的任务
    private Object lock; //锁

    public ExecuteThread(Callable<V> task, Object lock) {
        this.task = task;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            //这里执行耗时任务
            result = task.call();
        } catch (Exception e) {
            exception = e;
        } finally {
            synchronized (lock) {
                done = true;
                lock.notifyAll();
            }
        }
    }

    public V getResult() {
        return result;
    }

    public boolean isDone() {
        return done;
    }

    public Exception getException() {
        return exception;
    }
}
