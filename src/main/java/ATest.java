import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ATest {

    public static void main(String[] args) {

        ATest aTest = new ATest();
//        aTest.testBooleanArray();

//        aTest.testFinalize();

//        testCPULimit();

//        aTest.test();

        aTest.testPriorityQueue();

        /**
         * AtomicStampedReference可以避免ABA问题
         */
        Integer integer = 1;
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(integer, 1);
        atomicStampedReference.set(1, 1);
    }

//    private byte[] arr = new byte[1024 * 1024];

    /**
     * bool 数组中bool占byte大小，bool单个类型占int大小
     * 引用类型占int大小
     */
//    private int[] a = new int[1024 * 1024 * 2];
    private void testBooleanArray() {
//        a = null;

        System.out.println("==================================start==================================");
        System.gc();
        System.out.println("==================================end==================================");
    }


    public void testFinalize() {


        TESTT a = new TESTT("a");
        TESTT b = new TESTT("b");

        a.testt = b;
        b.testt = a;

        a = null;
        b = null;


        System.gc();

    }

    public static class TESTT {

        public TESTT testt;
        private String name;

        public TESTT(String name) {
            this.name = name;
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("TESTT_" + name + "$" + hashCode() + "finalize");
        }
    }

    public static void testCPULimit() {
        int coreCpu = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < coreCpu; i++) {
            new Thread(() -> {
                int i1 = 0;
                while (true) {
                    i1++;
                }
            }).start();
        }
    }

    private void test() {
        int a[] = new int[100];
        a[1] = 10;
        System.out.println(a.length);
    }


    private void testPriorityQueue() {
        PriorityQueue<TP> priorityQueue = new PriorityQueue<>();

        for (int i = 0; i < 5; i++) {
            priorityQueue.add(new TP(new Random().nextInt(5)));
        }

        while (!priorityQueue.isEmpty()) {
            System.out.println(priorityQueue.poll());
        }
    }

    public static class TP implements Comparable<TP> {
        int priority;

        public TP(int priority) {
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "TP{" +
                    "priority=" + priority +
                    '}';
        }

        @Override
        public int compareTo(@NotNull TP o) {
            return priority - o.priority;
        }
    }


    /**
     * 同步容器
     */
    public void testSynchronizedCollections() {
        Collections.synchronizedMap(new HashMap<>());
    }

    public void test222() {
        TestClass.ChildStatic childStatic = new TestClass.ChildStatic();
    }
}


