package reference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * Name: TestReferenceQueue
 * Author: lloydfinch
 * Function: TestReferenceQueue
 * Date: 2020-05-12 14:46
 * Modify: lloydfinch 2020-05-12 14:46
 */
public class TestReferenceQueue {

    public static void main(String[] args) {

        //System.gc(); //Runtime.getRuntime().gc();
        //System.runFinalization(); //Runtime.getRuntime().runFinalization();

        TestReferenceQueue test = new TestReferenceQueue();
        test.test();
    }


    private void test() {
        ReferenceQueue<Ref> queue = new ReferenceQueue<>();

//        Ref hello = new Ref();
//        Ref hello2 = new Ref();

        WeakReference<Ref> weak = new WeakReference<>(new Ref(), queue);
        SoftReference<Ref> soft = new SoftReference<>(new Ref(), queue);

        System.out.println("weak get 1: " + weak.get());
        System.out.println("soft get 1: " + soft.get());

        System.gc();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("weak get 2: " + weak.get());
        System.out.println("soft get 2: " + soft.get());


        System.out.println("====================================================================================================");
        Reference<? extends Ref> temp;
        while ((temp = queue.poll()) != null) {
            System.out.println(temp + " = " + temp.get());
        }
    }

    public class Ref {
    }
}
