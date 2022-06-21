package cn.orangepoet.inaction.algorithm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author chengzhi
 * @date 2022/06/05
 */
public class LRU {
    private final Map<String, String> map;

    public LRU(int capacity) {
        this.map = new LinkedHashMap<String, String>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return this.size() > capacity;
            }
        };
    }

    public String get(String key) {
        return map.get(key);
    }

    public void put(String key, String value) {
        this.map.put(key, value);
    }
}
