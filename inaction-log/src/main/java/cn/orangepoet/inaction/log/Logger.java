package cn.orangepoet.inaction.log;

import java.util.Map;

/**
 * Logger stands for log for business.
 * <p>
 * Created by Orange on 2017/1/10.
 */
public interface Logger {
    void info(String message, Map<String, String> tags);

    void warn(String warning, Map<String, String> tags);

    void warn(Exception ex, Map<String, String> tags);

    void error(Exception ex, Map<String, String> tags);
}
