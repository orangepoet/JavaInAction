package cn.orangepoet.inaction.spring.cache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class UserClient {
    @Cacheable(value = "user1", cacheResolver = "cacheResolver")
    public String getUser1(int param1) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return String.format("data->%s", param1);
    }

    @Cacheable(value = "user2", cacheResolver = "cacheResolver")
    public String getUser2(int param1) {
        return String.format("data->%s", param1);
    }
}
