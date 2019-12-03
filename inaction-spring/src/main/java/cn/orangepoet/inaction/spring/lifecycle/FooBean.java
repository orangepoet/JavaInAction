package cn.orangepoet.inaction.spring.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author chengzhi
 * @date 2019/12/03
 */
@Slf4j
@Component
public class FooBean implements InitializingBean {
    private DepBean depBean;

    public FooBean() {
        log.info("ctor");
    }

    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("afterPropertiesSet");
    }

    @Autowired
    public void setDep(DepBean depBean) {
        log.info("setter inject");
        this.depBean = depBean;
    }
}
