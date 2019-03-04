package producer2consumer;

/**
 * 生产者-消费者之产品
 */
public class Task {
    private String taskName;

    public Task(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                '}';
    }
}
