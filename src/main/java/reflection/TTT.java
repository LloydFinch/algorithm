package reflection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class TTT {

    public TTT(String identi) {
    }


    private TTT() {
    }


    public static void main(String[] args) throws ClassNotFoundException {
        TTT ttt = new TTT();
//        try {
//            ttt.test();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        ttt.testLoop();

//        ttt.testCPU();

//        ttt.testSkipMap();

//        testClassLoad();

//        ttt.recursion();

        System.out.println(Loader.a);
    }

    volatile boolean can = false;

    private void test() throws InterruptedException {
        Thread t1 = new Thread("SetThread") {
            @Override
            public void run() {
                while (!can) {
                }
                System.out.println("SetThread exit...");
            }
        };

        t1.start();
        Thread.sleep(1000);
        can = true;
        System.out.println("main exit...");
    }


    private void testLoop() {
        int a = 10;
        int b = 20;
        for (int i = 0; i < a; i++) {
            for (int j = 5; j < b; j++) {
                if (j == i) {
                    System.out.println("find " + j);
                    break;
                }
            }
            System.out.println("out loop " + i);
        }
    }

    long a = 10;

    private void testCPU() {
        int core = Runtime.getRuntime().availableProcessors();
        System.out.println("current core is " + core);
        int number = (core >> 1);
        for (int i = 0; i < number; i++) {
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        a++;
                    }
                }
            }.start();
        }
    }

    private void testSkipMap() {
        ConcurrentSkipListSet<String> skipListSet = new ConcurrentSkipListSet<>();
        skipListSet.add("d");
        skipListSet.add("b");
        skipListSet.add("c");
        skipListSet.add("e");
        skipListSet.add("a");
        System.out.println(skipListSet);
        System.out.println(skipListSet.first());

        ConcurrentSkipListMap<String, String> skipListMap = new ConcurrentSkipListMap<>();
        skipListMap.put("d", "d");
        skipListMap.put("b", "b");
        skipListMap.put("c", "c");
        skipListMap.put("e", "e");
        skipListMap.put("a", "a");
        System.out.println(skipListMap);

        System.out.println(skipListMap.get("b"));
    }


    public static void testClassLoad() throws ClassNotFoundException {

        for (int i = 0; i < 5; i++) {
            final String name = "thread" + i;
            Thread thread = new Thread(name) {
                @Override
                public void run() {
                    System.out.println(name + " start");
                    try {
                        Class.forName(Loader.class.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + " end");
                }
            };
            thread.start();
        }
    }

    /**
     * 测试类加载：加载，链接(验证，准备，解析)，初始化，使用，卸载
     * <p>
     * <p>
     * 加载: .java转换成.class的过程: .java先转换成二进制流，然后以虚拟机需要的结构存储在方法区，并在方法区生成.class文件
     * 验证: 验证数据是否合理(跳过)
     * 准备: 读取所有static类型的变量赋零值(eg:int,long等零值是0，Object等是null)，如果是编译期常量，则直接赋指定的初始值
     * eg:static int a = 10;这里就是0，static final int b = 10;这里就是10, static final long c = System.currentMillis(); 这里是0
     * 解析: 符号解析(跳过)
     * 初始化: 将所有static代码块和赋值语句结合生成clinit()方法，并执行。上述的static int a = 10,这里是10了(c同理)
     */
    public static class Loader implements FF {
        static {
            System.out.println("static init1, assign a to 20");
            System.out.println("static init1,  b=" + b);

            /**
             * 这里来个死循环，会导致其他加载这个类的线程全部卡住
             */
//            if (true) {
//                while (true) {
//
//                }
//            }

            System.out.println("static init1 finish");
        }

        public static final long a = System.currentTimeMillis();

        static {
            System.out.println("static init2, a=" + a);
        }

        static {
            System.out.println("static init3, a=" + a);
        }

        {
            System.out.println("init, a=" + a);
        }
    }


    interface FF {
        int b = 10;
    }

    public int recursionCount = 16386;

    public void recursion() {
        recursionCount--;
        if (recursionCount > 0) {
            recursion();
        }


        /**
         * 处理第一部分
         */
        {
            long l = 1000L;

        }

        /**
         * 处理第二部分
         */
        {
            Object o = new Object();


        }
    }
}