package cn.orangepoet.inaction;

import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableCaching
@Slf4j
public class Application {
    @Resource
    private RemoteClient remoteClient;
    @Resource
    private UserClient userClient;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return (args) -> {
            for (int i = 0; i < 3; i++) {
                log.info("remoteClient.getData..., times: {}", i);
                String data = remoteClient.getData(String.valueOf(1));
                log.info("data: {}", data);
            }

            for (int i = 0; i < 3; i++) {
                log.info("userClient.getUser1..., times: {}", i);
                String user1 = userClient.getUser1(1);
                log.info("user1: {}", user1);
            }
        };
    }

    @Bean
    @Primary
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();

        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES));
        return caffeineCacheManager;
    }

    @Bean
    public CacheManager concurrentCacheManager() {
        return new ConcurrentMapCacheManager("data", "user1");
    }

    @Bean
    public CacheResolver cacheResolver(@Qualifier("caffeineCacheManager") CacheManager caffeineCacheManager, @Qualifier("concurrentCacheManager") CacheManager simpleCacheManager) {
        return new MultipleCacheResolver(caffeineCacheManager, simpleCacheManager);
    }
}