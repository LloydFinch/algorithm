import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {

    public static void main(String[] args) {
//        println(testReturn());

//        Lock lock = new ReentrantLock();
//        lock.lock();
//        lock.unlock();
//        int number = 100;
//        for (int i = 0; i < number; i++) {
//            new Account1(lock).start();
//        }

        //===============================
        //测试一下避免死锁的案例
        testTryLockDemo();
    }

    public static int testReturn() {
        int a = 10;
        try {
            return a;
        } finally {
            a = 12;
            println("in finally: " + a);
        }
    }

    public static void testLock() {

    }


    /**
     * 使用synchronized实现的同步安全的加法器
     */
    public static class Counter1 {
        private volatile int count;

        /**
         * 不能同时增加
         * 这里会阻塞
         */
        public synchronized void incr() {
            count++;
        }

        public int get() {
            return count;
        }
    }

    /**
     * 使用显式锁实现的加法器
     */
    public static class Counter2 {
        private volatile int count;
        private Lock mLock = new ReentrantLock();

        public void incr() {
            mLock.lock(); //加锁
            try {
                count++; //执行操作
            } finally {
                //一般放在finally中保证一定执行
                mLock.unlock();//释放锁
            }
        }

        public int getCount() {
            return count;
        }
    }

    public static class Account1 extends Thread {
        private Lock lock;

        public Account1(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.tryLock();
        }
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }

    public static void testTryLockDemo() {
        int count = 10;
        double money = 10000;
        //10个账户
        Account[] accounts = new Account[count];
        for (int i = 0; i < count; i++) {
            accounts[i] = new Account(money);
        }

        Bank bank = new Bank();
        //10个柜台
        Thread[] threads = new Thread[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new Thread(() -> {
                //转账100次
                for (int j = 0; j < 100; j++) {
                    Account from = accounts[new Random().nextInt(count)];
                    Account to = accounts[new Random().nextInt(count)];
                    double money2 = new Random().nextInt(100);
                    if (from != to) {
                        try {
                            bank.transfer(from, to, money2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            });
            threads[i].start();
        }
    }

    /**
     * 模仿账户
     */
    public static class Account {
        private Lock lock = new ReentrantLock();
        private volatile double money = 0;

        public Account(double money) {
            this.money = money;
        }

        /**
         * 存钱
         */
        public void add(double money) {
            lock.lock();
            try {
                this.money -= money;
            } finally {
                lock.unlock();
            }
        }

        /**
         * 取钱
         */
        public void reduce(double money) {
            lock.lock();
            try {
                this.money -= money;
            } finally {
                lock.unlock();
            }
        }

        public double getMoney() {
            return money;
        }

        public void lock() {
            lock.lock();
        }

        public void unlock() {
            lock.unlock();
        }

        public boolean tryLock() {
            return lock.tryLock();
        }
    }

    /**
     * 银行
     */
    public static class Bank {
        private Exception noEnoughMoneyExeception = new Exception("no enough money!");

        /**
         * 转账
         *
         * @param from
         * @param to
         * @param money
         */
        public void transfer(Account from, Account to, double money) throws Exception {
            if (from.tryLock()) {//使用tryLock()避免死锁
                try {
                    if (to.tryLock()) {
                        try {
                            if (from.getMoney() >= money) {
                                from.reduce(money);
                                to.add(money);
                                println("transfer money:" + money);
                            } else {
                                throw noEnoughMoneyExeception;
                            }
                        } finally {
                            to.unlock();
                        }
                    } else {
                        println("to no lock");
                    }
                } finally {
                    from.unlock();
                }
            } else {
                println("from no lock");
            }
        }

//        //这样发生死锁
//        public void transfer(Account from, Account to, double money) throws Exception {
//            from.lock(); //这个可能死锁:互相同时转账
//            try {
//                to.lock();
//                try {
//                    if (from.getMoney() >= money) {
//                        from.reduce(money);
//                        to.add(money);
//                        println("transfer money:" + money);
//                    } else {
//                        throw noEnoughMoneyExeception;
//                    }
//                } finally {
//                    to.unlock();
//                }
//            } finally {
//                from.unlock();
//            }
//        }
    }
}
