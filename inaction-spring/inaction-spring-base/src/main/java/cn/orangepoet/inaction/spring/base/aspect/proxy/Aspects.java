package cn.orangepoet.inaction.spring.base.aspect.proxy;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author chengzhi
 * @date 2020/05/03
 */
@Slf4j
@Aspect
@Component
public class Aspects {
    @Pointcut("execution(public void ProxySelfMethod.method1())")
    private void method1() {}

    @Pointcut("execution(public void ProxySelfMethod.method2())")
    private void method2() {}

    @Before("method1()")
    public void traceMethod1() {
        log.info("method1 start...");
    }

    @Before("method2()")
    public void traceMethod2() {
        log.info("method2 start...");
    }
}
