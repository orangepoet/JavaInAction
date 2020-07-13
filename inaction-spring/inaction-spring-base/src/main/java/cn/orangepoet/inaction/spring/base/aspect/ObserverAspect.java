package cn.orangepoet.inaction.spring.base.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author orangecheng
 */
@Component
@Aspect
public class ObserverAspect {

    private Logger logger = LoggerFactory.getLogger(ObserverAspect.class);

    @Pointcut("execution(public * cn.orangepoet.inaction.spring.base.aspect.SubjectImpl.onMessage1())")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        logger.info("doBefore");
    }

    @After("log()")
    public void doAfter(JoinPoint joinPoint) {
        logger.info("doAfter");
    }

    @AfterReturning(value = "log()", returning = "obj")
    public void doReturn(JoinPoint joinPoint, Object obj) {
        logger.info("doReturn");
    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("doAround");
        return proceedingJoinPoint.proceed();
    }
}
