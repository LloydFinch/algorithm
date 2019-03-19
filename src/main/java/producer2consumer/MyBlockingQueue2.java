package producer2consumer;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用显式锁和显式条件实现的高效的队列
 */
public class MyBlockingQueue2<E> {
    private int limit;
    private Queue<E> queue;
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public MyBlockingQueue2(int limit) {
        this.limit = limit > 1 ? limit : 1;
        queue = new ArrayDeque<>(limit);
    }

    public void put(E e) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (limit == queue.size()) {
                //满了，所有等待"不满"条件的线程挂起
                System.out.println("put await");
                notFull.await();
            }
            System.out.println("put notify empty");
            queue.add(e);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (queue.isEmpty()) {
                //空了，所有等待"不空"条件的线程挂起
                System.out.println("take await");
                notEmpty.await();
            }
            System.out.println("take notify full");
            E e = queue.poll();
            notFull.signal();
            return e;
        } finally {
            lock.unlock();
        }

    }

}
