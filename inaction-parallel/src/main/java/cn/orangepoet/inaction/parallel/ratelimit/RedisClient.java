package cn.orangepoet.inaction.parallel.ratelimit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RedisClient {
    private Map<String, AtomicInteger> map = new ConcurrentHashMap<>();

    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public int decrby(String key, int count) {
        AtomicInteger value = map.get(key);
        if (value == null) {
            return 0;
        }
        int result = value.get();
        for (int i = 0; i < count; i++) {
            result = value.decrementAndGet();
        }
        return result;
    }

    public int incr(String key, int seconds) {
        AtomicInteger value = map.get(key);
        if (value == null) {
            value = new AtomicInteger();
            AtomicInteger value0 = map.putIfAbsent(key, value);
            if (value0 != null) {
                value = value0;
            } else {
                expire(key, seconds);
            }
        }
        return value.incrementAndGet();
    }

    public Integer get(String topic) {
        AtomicInteger value = map.get(topic);
        if (value == null) {
            return null;
        }
        return value.get();
    }

    public void expire(String key, int seconds) {
        service.schedule(() -> {
            map.remove(key);
        }, seconds, TimeUnit.SECONDS);
    }

    public void close() {
        service.shutdown();
    }
}
