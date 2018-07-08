package cn.orangepoet.inaction.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ObserverAspect {

    private Logger logger = LoggerFactory.getLogger(ObserverAspect.class);

    @Pointcut("execution(public * cn.orangepoet.action.Subject.onMessage())")
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
}
