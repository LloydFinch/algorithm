package structure;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<K, V> extends LinkedHashMap<K, V> {
    private int maxLength;

    //增长因子，只要当前size大于最大size的0,75倍，就扩容
    private static final float loadFactor = 0.75f;

    public LruCache(int initialCapacity, int maxLength) {
        //这里传入true，表示最新访问的放在最后面
        super(initialCapacity, loadFactor, true);
        this.maxLength = maxLength;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        //设置缓存的最大限制，大于这个限制就清除最前面的数据
        return size() > maxLength;
//        return false;
    }
}
