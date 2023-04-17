package cn.orangepoet.inaction.wx.utils;

import lombok.NonNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author orange.cheng
 * @date 2023/4/13
 */
@Component
public class RedisClient {
//    @Resource
    private StringRedisTemplate redisTemplate;

    /**
     * get
     */
    public String get(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    public void set(String key, String value, long expire) {
        redisTemplate.boundValueOps(key).set(value, expire, TimeUnit.SECONDS);
    }

    public Boolean setIfAbsent(String key, String value, int expire, TimeUnit timeUnit) {
        return redisTemplate.boundValueOps(key).setIfAbsent(value, expire, timeUnit);
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    @NonNull
    public Set<String> zsetRange(String key, int start, int end) {
        Set<String> range = redisTemplate.boundZSetOps(key).range(start, end);
        return range != null ? range : Collections.emptySet();
    }

    public Long zsetBatchAdd(String key, Set<ZSetOperations.TypedTuple<String>> typedTuples, int expire, TimeUnit timeUnit) {
        Long add = redisTemplate.boundZSetOps(key).add(typedTuples);
        redisTemplate.expire(key, expire, timeUnit);
        return add;
    }

    public Long zsetCount(String key) {
        return redisTemplate.boundZSetOps(key).size();
    }

    public Long setAdd(String key, String value, int expire, TimeUnit timeUnit) {
        Long add = redisTemplate.boundSetOps(key).add(value);
        redisTemplate.expire(key, expire, timeUnit);
        return add;
    }

    public Long setCount(String key) {
        return redisTemplate.boundSetOps(key).size();
    }

    public Long lpush(String key, String... value) {
        return redisTemplate.boundListOps(key).leftPushAll(value);
    }

    public Long lpush(String key, String value, int expire, TimeUnit timeUnit) {
        Long ret = redisTemplate.boundListOps(key).leftPushAll(value);
        redisTemplate.expire(key, expire, timeUnit);
        return ret;
    }

    public String rpop(String key) {
        return redisTemplate.boundListOps(key).rightPop();
    }

    public String brpop(String key, int seconds) {
        return redisTemplate.boundListOps(key).rightPop(seconds, TimeUnit.SECONDS);
    }

    public Long listCount(String key) {
        return redisTemplate.boundListOps(key).size();
    }
}
