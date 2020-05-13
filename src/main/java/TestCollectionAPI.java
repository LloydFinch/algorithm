import structure.LruCache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;

public class TestCollectionAPI {

    public static void main(String args[]) {

        testMap();

//        testSet();

//        testTreeMap();

//        testTreeSet();

        //testLinkedHashMap();

//        long time1 = System.currentTimeMillis();
//        System.out.println(getCommonPre(new String[]{"flower", "flow", "fill", "fillow"}));
//        System.out.println((System.currentTimeMillis() - time1));

//    test2();
//        test();

//        testHashMapTable();
    }

    private static void add(TreeNode treeNode) {
//        treeNode.value = "100";

        TreeNode treeNode1 = new TreeNode();
        treeNode1.value = "100";

        treeNode = treeNode1;
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

        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();


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

        ConcurrentSkipListMap<String, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();
        String key = "key";
        String value = "value";
        concurrentSkipListMap.put(key, value);
        concurrentSkipListMap.get(key);
        concurrentSkipListMap.remove(key);
    }

    private static void testTreeSet() {
        //排重&有序
        //按照key有序
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("d");
        treeSet.add("a");
        treeSet.add("g");
        treeSet.add("f");
        treeSet.add("e");
        treeSet.add("ge");
        treeSet.add("ae");
        treeSet.add("ef");
        treeSet.add("fe");
        treeSet.forEach(System.out::println);

        TreeSet<Object> treeSet1 = new TreeSet<>();
        treeSet1.add(new Object());

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

        LinkedHashMap<String, String> linkedHashMap1 = new LinkedHashMap<>(10, 1, true);
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
    }

    public enum Size {
        SMALL, MEDIUM, LATGE
    }

    private static void testEnumMap() {
        //一个枚举map
        EnumMap<Size, String> enumMap = new EnumMap<>(Size.class);
    }

    private static void testEnumSet() {
        EnumSet<Size> enumSet = EnumSet.noneOf(Size.class);
        int count = enumSet.size();
        enumSet.add(Size.SMALL);
        enumSet.remove(Size.SMALL);

        BitSet bitSet = new BitSet(10);
        bitSet.set(1);
        System.out.println(bitSet.toString());
    }

    private static void testPriorityQueue() {
        //这玩意实现了堆
        PriorityQueue<String> priorityQueue = new PriorityQueue<>();
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

    public static void test2() {
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

        TreeNode treeNode = new TreeNode();
        treeNode.value = "10";
        add(treeNode);
        System.out.println(treeNode.value);

        System.out.println("-----------------------------------");
        testEnumSet();

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
    }

    private static void test() {
        String hello = "#123456###7855555#";
        String hello2 = "#123456#77777#666#";
        int start = hello.indexOf("#");
        int end = hello.indexOf("#", start + 1);
        String string = hello.substring(start, end + 1);
        System.out.println(start + ", " + end + ", " + string);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");

        System.out.println(setTextColorByFlag(hello, "#").toString());
    }

    /**
     * 测试HashMap和HashTable对null的支持
     */
    public static void testHashMapTable() {
        /**
         * key和value都可以为null
         */
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(null, null);
        hashMap.put(null, null);

        /**
         * key和value都不能为null
         */
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("hello", "world");

    }

    /**
     * 将content的两个flag之间的内容连同flag进行特殊标记
     *
     * @param content
     * @param flag
     * @return
     */
    public static CharSequence setTextColorByFlag(CharSequence content, String flag) {
        CharSequence result = content;
        if (content != null) {
            String str = result.toString();
            int lastFlagIndex = str.length() - 2; //因为最后必修有一个字符和一个#
            if (str.contains(flag)) {
                StringBuilder builder = new StringBuilder();
                int startOffset = 0; //表示从这里开始找
                int start = 0;//表示找到的开始位置
                int end = start + 1; //表示找到的结束位置
                while (start < lastFlagIndex) {
                    start = str.indexOf(flag, startOffset); //本次开始位置，可能跟上次的位置不连贯
                    if (start >= 0 && start < lastFlagIndex) {
                        //说明找到了第一个flag
                        //从第一个位置的下一个位置开始找
                        end = str.indexOf(flag, start + 1); //结束位置
                        if (end > 0) {
                            //在这里判断一下连接性，然后缀上
                            if (start > startOffset) {
                                String outStr = str.substring(startOffset, start);
                                builder.append("\n").append(outStr);
                                System.out.println("跨越了");
                            }

                            //表示找到了，截取找到的部分，添加底色
                            String s = str.substring(start, end + 1);
                            builder.append("\n").append(s);
                            //从下一个位置开始重新找
                            startOffset = end + 1;
                        } else {
                            //本轮找到开始标记但是没找到结束标记，缀上剩余字符串
                            String s = str.substring(startOffset);
                            builder.append("\n").append(s);
                            System.out.println("有开始没结束");
                            break;
                        }
                    } else {
                        //本轮没找到开始标记，缀上剩余的字符串
                        String s = str.substring(startOffset);
                        builder.append("\n").append(s);
                        System.out.println("没开始");
                        break;
                    }
                }
                return builder;
            }
        }
        return result;
    }
}
