package cn.orangepoet.inaction;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultipleCacheResolver implements CacheResolver {
    private CacheManager localCacheManager;
    private CacheManager remoteCacheManager;

    private boolean switchLocal = false;

    public MultipleCacheResolver(CacheManager localCacheManager, CacheManager remoteCacheManager) {
        this.localCacheManager = localCacheManager;
        this.remoteCacheManager = remoteCacheManager;
    }

    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
        log.info("resolveCaches...");

        final Collection<String> cacheNames = context.getOperation().getCacheNames();
        CacheManager cacheManager = switchLocal ? localCacheManager : remoteCacheManager;

        List<Cache> caches = cacheNames.stream().map(name -> cacheManager.getCache(name)).collect(Collectors.toList());

        log.info("caches: {}", caches);

        return caches;
    }
}
