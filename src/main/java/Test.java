import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class Test {

    public static void main(String args[]) {

        //testMap();

        //testSet();

        testTreeMap();
    }


    private static void testMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(null, "you mather");
        hashMap.put(null, "your father");

        for (String key : hashMap.keySet()) {
            System.out.println(key + ":" + hashMap.get(key));
        }
    }

    private static void testSet() {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("hello");
        hashSet.add(null);
        hashSet.add("hello");

        for (String str : hashSet) {
            System.out.println(str);
        }
    }

    private static void testTreeMap() {
        //使用忽略大小的比较器，那么a和A就是相同的，所以下面的比较结果a和A只会保存一个
        //为a=A,健为第一次put的，值为后面put(健已经存在就只覆盖值)
        TreeMap<String, String> treeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        treeMap.put("X", "X");
        treeMap.put("a", "a");
        treeMap.put("d", "d");
        treeMap.put("c", "c");
        treeMap.put("b", "b");
        treeMap.put("A", "A");


        for (Map.Entry<String, String> kv : treeMap.entrySet()) {
            System.out.println(kv.getKey() + ":" + kv.getValue());
        }
    }
}
