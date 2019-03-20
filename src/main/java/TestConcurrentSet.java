import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 并发容器
 */
public class TestConcurrentSet {

    public static void main(String[] args) {
        int a = ((1 << 5) << 10) << 10;
        System.out.println(a / 1024 / 1024);
        test();
    }

    public static void test() {
        //并发的List
        CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();
        String hello = "hello";
        boolean b = arrayList.addIfAbsent(hello);
        boolean b1 = arrayList.addIfAbsent(hello);
        println(b);
        println(b1);

        int i = arrayList.addAllAbsent(Arrays.asList("hello", "android"));
        println(i);

        arrayList.add(hello); //不可并行
        arrayList.get(0); //可并行
        arrayList.indexOf(hello);

        //并发的Set
        CopyOnWriteArraySet<String> arraySet = new CopyOnWriteArraySet<>();
        arrayList.add(hello);//通过CopyOnWriteArrayList.putIfAbsent()实现去重的


        //并发的HashMap
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.putIfAbsent(hello, hello);
        concurrentHashMap.replace(hello, hello, hello);

        //并发的HashSet
        Set<String> set = Collections.newSetFromMap(new ConcurrentHashMap<>());

        //并发的TreeMap
        ConcurrentSkipListMap<String, String> skipListMap = new ConcurrentSkipListMap<>();
        skipListMap.size();//需要遍历元素，效率很低，用途不大

        //并发的TreeSet
        ConcurrentSkipListSet<String> skipListSet = new ConcurrentSkipListSet<>();


        //并发队列==============================================


    }

    public static void println(Object o) {
        System.out.println(o);
    }
}
