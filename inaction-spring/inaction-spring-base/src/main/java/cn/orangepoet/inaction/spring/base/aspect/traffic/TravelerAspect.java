package cn.orangepoet.inaction.spring.base.aspect.traffic;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 通过限定方法名, 加入before 通知方法,
 * 连接点即可以是自己方法, 也可以是第三方方法 (public类型)
 */
@Aspect
@Component
public class TravelerAspect {
    @Pointcut("execution(public * cn.orangepoet.inaction.spring.base.aspect.traffic.Traveler.*(..))")
    public void go() {
    }

    @Pointcut("execution(public * java.util.List.add(..))")
    public void listAdd() {
    }

    @Before("go()")
    public void beforeGo() {
        System.out.println("beforeGo");
    }

    @Before("listAdd()")
    public void beforeListAdd() {
        System.out.println("beforeListAdd");
    }
}
