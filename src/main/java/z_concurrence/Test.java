package z_concurrence;

public class Test {

    public static void main(String[] args) {
        stopThreadByInterrupt();
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


    private static void println(Object o) {
        System.out.println(o);
    }
}
