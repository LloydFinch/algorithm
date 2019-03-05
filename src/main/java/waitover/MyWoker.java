package waitover;

public class MyWoker extends Thread {

    private MyLatch myLatch;

    public MyWoker(MyLatch myLatch) {
        this.myLatch = myLatch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((int) (Math.random() * 1000));
            myLatch.countDown();
        } catch (InterruptedException e) {

        }
    }
}
