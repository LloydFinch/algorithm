package producer2consumer;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 模仿生产者/消费者队列
 */
public class MyBlockingQueue<E> {
    private int limit;
    private final int minLimit = 1;
    private Queue<E> queue = null;

    public MyBlockingQueue(int limit) {
        this.limit = limit > minLimit ? limit : minLimit; // 这里limit为0的话，就会发生死锁
        this.queue = new ArrayDeque<>(this.limit);
    }

    /**
     * 生产
     *
     * @param e
     */
    public synchronized void put(E e) throws InterruptedException {
        if (queue.size() == limit) {
            System.out.println("full, wait consume...");
            wait();
        }
        System.out.println("full, notify consume...");
        queue.add(e);
        notifyAll();//唤醒其他等待当前对象的线程
    }

    /**
     * 消费
     *
     * @return
     */
    public synchronized E take() throws InterruptedException {
        if (queue.isEmpty()) {
            System.out.println("empty,wait produce...");
            wait();
        }
        System.out.println("empty, notify produce...");
        E e = queue.poll();
        notifyAll();
        return e;
    }
}


