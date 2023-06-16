package cn.orangepoet.inaction.spring.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Slf4j
@Configuration
public class CacheConfig {

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