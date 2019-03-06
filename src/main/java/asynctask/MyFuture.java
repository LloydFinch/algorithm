package asynctask;

/**
 * 异步任务的任务结果，从这里获取执行结果
 */
public interface MyFuture<V> {
    V get() throws Exception;
}
