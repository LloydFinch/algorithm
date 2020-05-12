import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Name: TestDamao
 * Author: lloydfinch
 * Function: damao
 * Date: 2020-04-15 10:26
 * Modify: lloydfinch 2020-04-15 10:26
 */
public class TestDamao {

    public static void main(String[] args) {
        TestDamao damao = new TestDamao();
//        damao.testSemaphore();
//        damao.testAtomicInt();
//        damao.testSynchronized();

//        damao.test();

//        damao.test2();

        damao.testList();
    }


    private void testSemaphore() {
        int permits = 10; //允许10个线程同时访问
        Semaphore semaphore = new Semaphore(permits);

        //这里开20个线程去访问
        int count = 20;
        for (int i = 0; i < count; i++) {
            new Thread(() -> doSomething(semaphore)).start();
        }
    }

    private void doSomething(Semaphore semaphore) {
        if (semaphore.tryAcquire()) {
            //获取到许可证，可以执行
            System.out.println("do");
        } else {
            //未获取到许可证，不能执行
            System.out.println("can not do");
        }

        //方法执行完毕，释放许可证
        semaphore.release();
    }


    private void testAtomicInt() {
        //最大允许执行数
        int permits = 10;
        AtomicInteger atomicInteger = new AtomicInteger(0);

        //这里开20个线程去访问
        int count = 20000;
        for (int i = 0; i < count; i++) {
            new Thread(() -> doSomething2(atomicInteger, permits)).start();
        }
    }

    private void doSomething2(AtomicInteger atomicInteger, int max) {
        if (atomicInteger.incrementAndGet() > max) {
            //超出执行数量，不执行
            System.out.println("can not do");
        } else {
            //可以执行
            System.out.println("do");
        }

        //执行完毕，释放
        atomicInteger.decrementAndGet();
    }

    private void testSynchronized() {
        //这里开20个线程去访问
        int count = 20000;
        for (int i = 0; i < count; i++) {
            new Thread(() -> doSomething()).start();
        }
    }

    private final int max = 10;
    private volatile int count = 0;

    private synchronized void doSomething() {
        if (++count > max) {
            //超出执行数量，不能执行
            System.out.println("can not do");
        } else {
            //可以执行
            System.out.println("do");
        }

        //执行完毕，释放
        count--;
    }

    private void test() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                int i = 10;
                //检测是否中断
                while (!isInterrupted()) {
                    i++;
                }
            }
        };
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //中断
        thread.interrupt();
    }

    private volatile boolean interrupt = false;

    private void test2() {
        Thread thread = new Thread() {
            @Override
            public void run() {

                while (!interrupt) {
                    System.out.println("running");
                }
            }
        };

        thread.start();

        try {

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        interrupt = true;
    }

    private void testAPI() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        System.out.println("hello hello");
        lock.unlock();


        Thread thread = new Thread() {
            @Override
            public void run() {
                //挂起
                LockSupport.park();
            }
        };

        //唤醒
        LockSupport.unpark(thread);
    }


    public interface I1 {
    }

    public interface I2 {
    }

    public interface I3 extends I1, I2 {
    }


    /**
     * 对fori和foreach的遍历删除专项测试
     * 1 fori遍历删除 ok
     * 2 foreach遍历删除 ConcurrentModificationException
     * 3 iterator遍历通过datas删除 ConcurrentModificationException
     * 4 iterator遍历通过iterator删除 ok
     */
    private void testList() {

        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("string-" + i);
        }
        datas.add("string-" + 1);
        datas.add("string-" + 5);

        String str1 = "string-1";
        String str5 = "string-5";

        /**
         * 根据内容删除
         */
        {
            //一般的for循环，datas删除，ok
//            {
//                for (int i = 0; i < datas.size(); i++) {
//                    String data = datas.get(i);
//                    if (str1.equals(data) || str5.equals(data)) {
//                        datas.remove(data);
//                    }
//                }
//                System.out.println("for-i finish, size is " + datas.size());
//            }

            //foreach遍历，datas删除，ConcurrentModificationException
//            {
//                for (String data : datas) {
//                    if (str1.equals(data) || str5.equals(data)) {
//                        datas.remove(data);
//                    }
//                }
//                System.out.println("foreach finish, size is " + datas.size());
//            }

            //iterator遍历，datas删除，ConcurrentModificationException
            /**
             * 分析，foreach遍历使用的是迭代器，如果删除使用的是datas.remove()，就跟下述写法一样，会crash
             * 猜测：用foreach遍历，使用迭代器删除[不行，无法嵌入]
             */
//        {
//            Iterator<String> iterator = datas.iterator();
//            while (iterator.hasNext()) {
//                String data = iterator.next();
//                if (str1.equals(data) || str5.equals(data)) {
//                    datas.remove(data);
//                }
//            }
//            System.out.println("for-iterator finish, size is " + datas.size());
//        }

            //iterator循环，iterator删除，ok
//        {
//            Iterator<String> iterator = datas.iterator();
//            while (iterator.hasNext()) {
//                String data = iterator.next();
//                if (str1.equals(data) || str5.equals(data)) {
//                    iterator.remove();
//                }
//            }
//            System.out.println("for-iterator finish, size is " + datas.size());
//        }
        }

        /**
         * 根据下标删除
         */
//        {
//
//            //一般的for循环，删除，ok
//            {
////                for (int i = 0; i < datas.size(); i++) {
////                    String data = datas.get(i);
////                    if (str1.equals(data) || str5.equals(data)) {
////                        datas.remove(i);
////                    }
////                }
//
//                //全部删除，根据下标删除
//                for (int i = 0; i < datas.size(); i++) {
//                    datas.remove(i);
//                }
//
//                //全部删除，根据内容删除
////                for (int i = 0; i < datas.size(); i++) {
////                    datas.remove(datas.get(i));
////                }
//
//                System.out.println("for-i finish, size is " + datas.size());
//            }
//        }

        //foreach遍历，iterator删除
        {
            int size = datas.size();
            for (int i = size - 1; i >= 0; i--) {
                String temp = datas.get(i);
                if (str1.equals(temp)) {
                    datas.remove(temp);
                }
            }
        }


        printList(datas);
    }


    private void printList(List<? extends Object> list) {
        if (list.isEmpty()) {
            return;
        }

        list.forEach(o -> System.out.println(o.toString()));
    }

}

