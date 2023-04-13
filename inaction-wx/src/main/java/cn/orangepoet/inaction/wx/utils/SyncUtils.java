package cn.orangepoet.inaction.wx.utils;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

/**
 * 同步任务工具类
 *
 * @author orange.cheng
 * @date 2023/3/22
 */
@Component
public class SyncUtils {
    private static final String REDIS_KEY_APP_SYNC_LOCK_FORMAT = "flotage.app_sync.lock.%s";
    private static final String REDIS_KEY_APP_SYNC_FORMAT = "flotage.app.sync.progress.%s";

    @Autowired
    private RedisClient redisClient;


    /**
     * 公众号同步锁
     *
     * @param appId
     * @return
     */
    public boolean lockAppSync(String appId) {
        String lockKey = String.format(REDIS_KEY_APP_SYNC_LOCK_FORMAT, appId);
        return redisClient.setIfAbsent(lockKey, "1", 1, TimeUnit.DAYS);
    }

    /**
     * 公众号同步解锁
     *
     * @param appId
     */
    public void unlockAppSync(String appId) {
        String lockKey = String.format(REDIS_KEY_APP_SYNC_LOCK_FORMAT, appId);
        redisClient.delete(lockKey);
    }

    /**
     * 读取同步状态, 内部受redis block pop 存在阻塞, 超时时间: 10秒
     *
     * @param appId
     * @return
     */
    public Double getAppSyncProgress(String appId) {
        String key = String.format(REDIS_KEY_APP_SYNC_FORMAT, appId);
        String value = redisClient.brpop(key, 5);
        return StringUtils.isNotBlank(value) ? Double.parseDouble(value) : null;
    }

    /**
     * 更新同步状态
     *
     * @param appId
     * @param curr
     * @param max
     */
    public void appendAppSyncProgress(String appId, int curr, int max) {
        Preconditions.checkArgument(curr >= 0);
        Preconditions.checkArgument(max > 0);

        int curr0 = Math.min(curr, max);
        double progress = BigDecimal.valueOf(curr0).divide(BigDecimal.valueOf(max), 4, RoundingMode.HALF_DOWN).doubleValue();

        String key = String.format(REDIS_KEY_APP_SYNC_FORMAT, appId);
        redisClient.lpush(key, String.valueOf(progress), 5, TimeUnit.MINUTES);
    }
}
