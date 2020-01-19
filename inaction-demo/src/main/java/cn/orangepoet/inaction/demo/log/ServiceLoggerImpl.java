package cn.orangepoet.inaction.demo.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * A simple implement of Logger
 * </p>
 * Created by Orange on 2017/1/10.
 */
@Component
public class ServiceLoggerImpl implements ServiceLogger {
    protected final Map<String, String> commonTags = new HashMap<>();

    @Autowired
    @Qualifier("CLogger")
    private Logger logger;

    private String serviceName;


    private String threadId;
    private final Object syncLock = new Object();

    @Override
    public void info(String message, Map<String, String> tags) {
        logger.info(message, extended(tags));
    }

    @Override
    public void warn(String warning, Map<String, String> tags) {
        logger.warn(warning, extended(tags));
    }

    @Override
    public void warn(Exception ex, Map<String, String> tags) {
        logger.warn(ex, extended(tags));
    }

    @Override
    public void error(Exception ex, Map<String, String> tags) {
        logger.error(ex, extended(tags));
    }

    public String getServiceName() {
        return serviceName;
    }


    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }


    private Map<String, String> extended(Map<String, String> tags) {
        fillCommandTags();
        if (tags == null || tags.size() == 0)
            return commonTags;

        TagsBuilder builder = new TagsBuilder();
        builder.appendAll(commonTags);
        if (tags != null && tags.size() > 0) {
            builder.appendAll(tags);
        }
        return builder.build();
    }

    private void fillCommandTags() {
        if (commonTags.size() == 0) {
            synchronized (syncLock) {
                if (commonTags.size() == 0) {
                    commonTags.put("service", getServiceName());
                    commonTags.put("thread", getThreadId());
                }
            }
        }
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
