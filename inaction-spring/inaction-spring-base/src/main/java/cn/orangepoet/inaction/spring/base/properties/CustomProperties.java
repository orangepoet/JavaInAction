package cn.orangepoet.inaction.spring.base.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chengzhi
 * @date 2019/12/11
 */
@ConfigurationProperties(
    prefix = "cz"
)
public class CustomProperties {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
