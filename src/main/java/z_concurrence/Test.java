package z_concurrence;

public class Test {

    public static void main(String[] args) {
//        stopThreadByInterrupt();

//        testThreadWaitInThread();

//        testThreadGroup();

        testDemon();
    }

    /**
     * 通过中断停止线程
     */
    private static void stopThreadByInterrupt() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    //添加对中断的检测，来退出线程
                    if (isInterrupted()) {
                        println("thread is interrupt");
                        break;
                    }
                    Thread.yield();
                }
            }
        };
        thread.start();

        try {
            Thread.sleep(1000);
            println("before interrupt, thread state: " + thread.getState());
            thread.interrupt();
            Thread.sleep(2000); //保证中断完成，才打印下面的语句
            println("after interrupt, thread state: " + thread.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
            //中断抛出异常会清除中断标记位
        }
    }


    /**
     * 在Thread2上调用Thread1.wait()，停止的是Thread2
     */
    private static void testThreadWaitInThread() {
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                println("in thread t1...");
            }
        });
        t1.start();


        Thread t2 = new Thread(() -> {
            try {
                synchronized (t1) {
                    println("in thread2 before thread1 wait");
                    t1.wait(); //这里的t1就是一个object
                    println("in thread2 after thread1 wait");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t2.start();

        try {
            Thread.sleep(10000);
            synchronized (t1) {
                t1.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 线程组
     */
    private static void testThreadGroup() {
        ThreadGroup threadGroup = new ThreadGroup("ThreadGroup");
        Thread t1 = new Thread(threadGroup, new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            println("t1");
        }), "t1");
        Thread t2 = new Thread(threadGroup, new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            println("t2");
        }), "t2");

        t1.start();
        t2.start();
        println(threadGroup.activeCount());
        threadGroup.list();
    }

    /**
     * 守护线程
     */
    private static void testDemon() {
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(1000);
                    println("I am daemon thread");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true); //设为守护线程
        thread.start();

        try {
            Thread.sleep(10000); //让主线程10s后再退出
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 住重点
     */
    private static void warning() {
        Integer integer = 0;
        synchronized (integer) {
            integer++; //这里有风险，因为integer不在-128-127之内会创建新对象，加锁的对象就变了
            println(integer);
        }
    }

    /**
     * JDK并发包
     */
    private static void jdkTools() {

    }

    private static void println(Object o) {
        System.out.println(o);
    }
}
