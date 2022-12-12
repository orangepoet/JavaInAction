package cn.orangepoet.inaction;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class RemoteClient {
    @Cacheable(value = "data", cacheResolver = "cacheResolver")
    public String getData(String param1) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return String.format("data->%s", param1);
    }

    @Cacheable(value = "data2", cacheResolver = "cacheResolver")
    public String getData2(int param1) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return String.format("data->%s", param1);
    }
}
