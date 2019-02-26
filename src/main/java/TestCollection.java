import java.util.*;

public class TestCollection {
    public static void main(String[] args) {

    }

    private static void testCollectionsAPI() {
        List<String> datas = new ArrayList<>();
        String key = "hello";
        Collections.binarySearch(datas, key);
        Collections.max(datas);
        Collections.min(datas);
        Collections.frequency(datas, key); //获取出现的次数
        boolean hasJoint = Collections.disjoint(datas, datas); //是否有交集

        Collection<String> collection = Collections.singleton("hello");
        Set<String> set = Collections.singleton("hello");
        List<String> stringList = Collections.singletonList("hello");
        Map<String, String> stringStringMap = Collections.singletonMap("key", "value");
    }
}
