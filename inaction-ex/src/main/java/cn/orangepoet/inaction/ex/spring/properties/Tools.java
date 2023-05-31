package cn.orangepoet.inaction.ex.spring.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chengzhi
 * @date 2019/12/11
 */
@Slf4j
@Component
public class Tools {
    @Autowired
    private CustomProperties customProperties;

    private int i = 0;

    public Tools() {
        i = 1;
    }

    public void showName() {
        log.info("name: " + customProperties.getName());
    }

    public int getI() {
        return i;
    }
}
