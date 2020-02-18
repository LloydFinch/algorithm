package annotation;

/**
 * 注解的成员必须是编译期常量
 */
@Route(path = Protocol.PATH, test = Protocol.a)
public class Test {
    public static void main(String[] args) {
        System.out.println("hello");


        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("current state:" + getState());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        /**
         * 通过run调用，那么线程一致都是NEW
         */
        thread.run();

        try {
            Thread.sleep(2000);
            System.out.println(thread.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
