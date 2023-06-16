package cn.orangepoet.inaction.spring.lifecycle;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chengzhi
 * @date 2022/06/01
 */
@Data
@Slf4j
public class MyBean implements BeanNameAware, BeanFactoryAware, ApplicationContextAware {
    public static final AtomicInteger versions = new AtomicInteger();

    public MyBean(String f1, String f2) {
        log.info("call constructor");
        this.f1 = f1;
        this.f2 = f2;
    }

    @PostConstruct
    public void init() {
        log.info("call PostConstruct");
    }

    private final String f1;
    private final String f2;

    private String f3;

    public void setF3(String f3) {
        log.info("call setProperties");
        this.f3 = f3;
    }

    private int version;

    public void setVersion() {
        log.info("call init-method");
        this.version = versions.incrementAndGet();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info("call setBeanFactory");
    }

    @Override
    public void setBeanName(String s) {
        log.info("call setBeanName");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("call setApplicationContext");
    }
}
