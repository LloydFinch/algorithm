package waitover;

/**
 * 等待所有的都到达
 * 等待线程等待，然后所有的执行线程都一起跑，到了就修改信号，当信号合法就notifyAll
 */
public class Test {

    public static void main(String[] args) {
        int num = 10;
        MyLatch myLatch = new MyLatch(num);
        MyWoker[] workers = new MyWoker[num];
        for (int i = 0; i < num; i++) {
            workers[i] = new MyWoker(myLatch);
            workers[i].start();
        }

        try {
            myLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("all worker run finished");
    }
}
