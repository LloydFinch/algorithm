package producer2consumer;

public class Test {
    public static void main(String[] args) {
        //构建商品队列
        MyBlockingQueue<Task> taskMyBlockingQueue = new MyBlockingQueue<>(0);
        Producer producer = new Producer(taskMyBlockingQueue);
        Consumer consumer = new Consumer(taskMyBlockingQueue);

        producer.start();
        consumer.start();
    }
}
