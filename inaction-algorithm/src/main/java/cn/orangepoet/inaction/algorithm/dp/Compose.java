package cn.orangepoet.inaction.algorithm.dp;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author chengzhi
 * @date 2022/05/24
 */
public class Compose {
    public static void main(String[] args) {
        LRU lru = new LRU(5);
        lru.put("1", "a");
        System.out.println(lru.get("1"));

        lru.put("2", "b");
        lru.put("3", "c");
        lru.put("4", "d");

        lru.get("1");

        lru.put("5", "d");
        lru.put("6", "e");

        System.out.println(lru.get("1"));
        System.out.println(lru.get("2"));
    }

    public static class LRU {
        private final Map<String, String> map;
        private int capacity;

        public LRU(int capacity) {
            this.capacity = capacity;
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
}
