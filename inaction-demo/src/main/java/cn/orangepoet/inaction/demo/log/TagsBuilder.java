package cn.orangepoet.inaction.demo.log;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Orange on 2017/1/15.
 */
public class TagsBuilder {
    private Map<String, String> tags = new HashMap<>();


    public TagsBuilder() {
    }

    public TagsBuilder append(String name, String value) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("name is blank");
        }
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("value is blank");
        }

        if (!tags.containsKey(name)) {
            tags.put(name, value);
        } else {
            throw new IllegalArgumentException("name is already defined in tags");
        }
        return this;
    }

    public TagsBuilder appendAll(Map<String, String> tags) {
        if (tags != null && tags.size() > 0) {
            this.tags.putAll(tags);
        }
        return this;
    }

    public Map<String, String> build() {
        return tags;
    }
}
