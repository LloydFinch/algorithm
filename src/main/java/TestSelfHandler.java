import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestSelfHandler {

    private static ConcurrentLinkedQueue<Runnable> messageQueue = new ConcurrentLinkedQueue<>();
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            messageQueue.offer(() -> {
                System.out.println("in which thread? " + Thread.currentThread().getName());
            });
            condition.signal();
            lock.unlock();
        }).start();

        //主线程开始
        //循环检测
        while (true) {
            lock.lock();
            try {
                while (messageQueue.isEmpty()) {
                    condition.await();
                    Runnable poll = messageQueue.poll();
                    if (poll != null) {
                        poll.run();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
