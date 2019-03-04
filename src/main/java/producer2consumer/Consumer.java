package producer2consumer;

public class Consumer extends Thread {

    private MyBlockingQueue<Task> taskMyBlockingQueue;

    public Consumer(MyBlockingQueue<Task> taskMyBlockingQueue) {
        this.taskMyBlockingQueue = taskMyBlockingQueue;
    }

    @Override
    public void run() {
        try {
            //往死里消费
            while (true) {
                Task task = taskMyBlockingQueue.take();
                System.out.println("consume a task " + task);

                Thread.sleep((long) (Math.random() * 100));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
