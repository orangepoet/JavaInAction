package cn.orangepoet.inaction.spring.properties;

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

    public void showName() {
      log.info("name: "+ customProperties.getName());
    }
}
