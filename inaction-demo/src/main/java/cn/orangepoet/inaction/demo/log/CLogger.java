package cn.orangepoet.inaction.demo.log;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Orange on 2017/1/15.
 */
@Component
public class CLogger implements Logger {
    @Override
    public void info(String message, Map<String, String> tags) {
        String infoString = buildLogString(message, tags);
        System.out.println(infoString);
    }

    @Override
    public void warn(String warning, Map<String, String> tags) {
        String warnString = buildLogString(warning, tags);
        System.err.println(warnString);
    }

    @Override
    public void warn(Exception ex, Map<String, String> tags) {
        // TODO: 2017/1/11  fafa
    }

    @Override
    public void error(Exception ex, Map<String, String> tags) {
        // TODO: 2017/1/11
    }

    private String buildLogString(String info, Map<String, String> extTags) {
        return String.format("log_info: {info: \"%s\", tags: %s}", info, buildTagJsonString(extTags));
    }

    private String buildTagJsonString(Map<String, String> tags) {
        StringBuilder ret = new StringBuilder();
        ret.append("{");

        if (tags != null && tags.size() > 0) {
            for (Map.Entry<String, String> entry : tags.entrySet()) {
                ret.append(String.format("%s=\"%s\",", entry.getKey(), entry.getValue()));
            }
        }
        int index = ret.lastIndexOf(",");
        if (index == ret.length() - 1) {
            ret.replace(index, index + 1, StringUtils.EMPTY);
        }
        ret.append("}");
        return ret.toString();
    }
}
