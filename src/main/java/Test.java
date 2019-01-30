import structure.LruCache;

import java.util.*;

public class Test {

    public static void main(String args[]) {

        //testMap();

        //testSet();

        //testTreeMap();

        //testLinkedHashMap();

//        long time1 = System.currentTimeMillis();
//        System.out.println(getCommonPre(new String[]{"flower", "flow", "fill", "fillow"}));
//        System.out.println((System.currentTimeMillis() - time1));


        String condition = new String("hello");
        System.out.println(condition == "hello");
        switch (condition) {
            case "hello":
                System.out.println(condition);
                break;
            default:
                break;
        }

        Integer integer = new Integer(10000000);
        System.out.println(integer.equals(10000000));
        switch (integer) {
            case 10000000:
                System.out.println(integer);
                break;
            default:
                break;
        }
        System.out.println(1.00d == 1.0f);

        System.out.println(0.1 + 0.2);
        boolean a = (0.3 - 0.2) == (0.2 - 0.1);
        System.out.println(a);

        System.out.println("\251");
    }

    private static void setVMPolicy() {

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
        //按健有序
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

    private static void testTreeSet() {
        //排重和有序
        //按健有序
        TreeSet<String> treeSet = new TreeSet<>();

    }

    private static void testLinkedHashMap() {
        //健按照插入顺序脾虚
        //这里开启了访问顺序，最近访问的放在最后面，可以用于LRU缓存
        LruCache<String, String> linkedHashMap = new LruCache<>(16, 4);
        linkedHashMap.put("a", "ahello");
        linkedHashMap.put("b", "bhello");
        linkedHashMap.put("c", "chello");
        linkedHashMap.put("d", "dhello");
        linkedHashMap.get("a");
        linkedHashMap.put("e", "ehello");
        System.out.println(linkedHashMap);
    }

    private static String getCommonPre(String[] strs) {
        String pre = "";
        StringBuilder pre1 = new StringBuilder();

        if (strs == null || strs.length <= 0) {
            return pre;
        }
        int length = strs.length;
        int len = (int) (Math.log(length) / Math.log(2)) + 1; //树的深度

        while (len > 0) {
            int currentLength = (length >>> 1) + 1;
            for (int start = 0; start < currentLength; start++) {
                strs[start] = getCommonPre(strs[start], strs[length - start - 1], pre1);
            }
            len--;
            length = currentLength;
        }
        pre = strs[0];
        return pre;
    }

    private static String getCommonPre(String s1, String s2, StringBuilder pre) {
        pre.delete(0, pre.length());
        char[] ch1 = s1.toCharArray();
        char[] ch2 = s2.toCharArray();
        int length = Math.min(ch1.length, ch2.length);
        for (int index = 0; index < length; index++) {
            if (ch1[index] != ch2[index]) {
                break;
            }
            pre.append(ch1[index]);
        }

        return pre.toString();
    }


    /**
     * 树节点
     * 根节点没有parent
     * 叶子节点没有left和right
     */
    public static class TreeNode {
        public TreeNode parent;
        public TreeNode left;
        public TreeNode right;
        public String value;

    }
}
