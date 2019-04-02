import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TestCompletableFuture {

    public static void main(String[] args) {
//        testFuture();
        testCompletableFuture();
    }

    /**
     * 这是使用future实现一个任务
     */
    private static void testFuture() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        });

        println("start...");
        String result = null;
        try {
            result = future.get(); //这里会阻塞等待
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        println("finish! result: " + result);
        executorService.shutdown(); //关闭任务


        //直接使用FutureTask实现一个任务
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            println("running...");
            return System.currentTimeMillis() + "";
        });
        futureTask.run();
    }

    /**
     * 使用CompletableFuture实现
     */
    private static void testCompletableFuture() {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return "hello1";
//        }, executorService);
//
//        println("start...");
//        String result = null;
//        try {
//            result = future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//        println("finish! result: " + result);
//        executorService.shutdown();

        //也可以使用类似FutureTask的写法
//        CompletableFuture<String> completableFuture = new CompletableFuture<>();
//        completableFuture.complete("hello");

        //可以写入回调，用来响应结果
        String join = CompletableFuture.supplyAsync(() -> {
            println("in thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).whenComplete((s, throwable) -> {
            // 这里是回调
            // 如果注册时任务还没结束，则由任务线程调用，否则注册线程调用
            //不希望阻塞则可以使用whenCompleteAsync
            println("finish, result is: " + s + ", " + Thread.currentThread().getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).whenComplete((s, throwable) -> {
            //后面添加的回调没什么卵用，因为前面的回调不能修改参数的结果，通过延时排队还可以
            println("finish2, result is: " + s + ", " + Thread.currentThread().getName());
        }).handle((s, throwable) -> {
            println("finish3 handle, result is: " + s + ", " + Thread.currentThread().getName());
            return "hello java"; //这个回调可以修改结果了，很风骚
        }).handle((s, throwable) -> {
            println("finish4 handle, result is: " + s + ", " + Thread.currentThread().getName());
            int a[] = new int[1];
//            int b = a[2]; //来个异常玩玩
            return s;
        }).exceptionally(throwable -> {
            //这个回调只处理异常,如果没有异常就不会回调
            println(throwable);
            return "hello"; //但是也需要返回值，异常的时候，返回结果就是这个了
        }).join();
        println("return main: " + join);

        //构建流
//        CompletableFuture.supplyAsync(() -> "hello1").thenRun(() -> {
//        }).thenAccept(String -> {
//        }).thenApply((Function<String, String>) s -> "hello2").join();
    }

    private static void println(Object object) {
        System.out.println(object);
    }
}
