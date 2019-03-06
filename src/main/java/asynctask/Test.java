package asynctask;

import java.util.concurrent.Callable;

/**
 * 异步等待结束
 * wait等待执行结果，结果来了notifyAll，然后接着执行wait后的代码去返回结果
 */
public class Test {

    public static void main(String[] args) {
        MyExecutor myExecutor = new MyExecutor();
        //这个是需要执行的任务
        Callable<Integer> task = () -> {
            //这个是跑在子线程中的
            Thread.sleep(3000);
            //模拟执行结果
            return 10086;
        };
        System.out.println("start running...");
        MyFuture<Integer> myFuture = myExecutor.execute(task);
        //得到执行结果
        try {
            Integer result = myFuture.get();
            System.out.println("the result is:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
