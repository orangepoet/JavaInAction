package cn.orangepoet.inaction.spring.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author chengzhi
 * @date 2022/06/01
 */
//@Component
@Slf4j
class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        if (s.equals("myBean")) {
            log.info("call BeanPostProcessor.postProcessBeforeInitialization:{}", s);
        }
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if (s.equals("myBean")) {
            log.info("call BeanPostProcessor.postProcessAfterInitialization:{}", s);
        }
        return o;
    }
}
