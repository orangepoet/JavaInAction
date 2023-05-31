package cn.orangepoet.inaction.ex.spring.aspect.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Component;

/**
 * @author chengzhi
 * @date 2020/05/03
 */
@Component
@Slf4j
public class ProxySelfMethod {
    public void method1() {
        log.info("method1");
        ((ProxySelfMethod)AopContext.currentProxy()).method2();
        //method2();
    }

    public void method2() {
        log.info("method2");
    }
}
