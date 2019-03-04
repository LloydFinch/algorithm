package producer2consumer;

/**
 * 生产者-消费者模型之生产者
 */
public class Producer extends Thread {

    private MyBlockingQueue<Task> taskMyBlockingQueue;

    public Producer(MyBlockingQueue<Task> taskMyBlockingQueue) {
        this.taskMyBlockingQueue = taskMyBlockingQueue;
    }

    @Override
    public void run() {
        int num = 0;
        try {
            while (true) {
                //往死里生产
                Task task = new Task("task" + num);
                taskMyBlockingQueue.put(task);
                System.out.println("produce a task " + task);

                num++;
                //每隔随机时间生产一个
                Thread.sleep((long) (Math.random() * 100));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
