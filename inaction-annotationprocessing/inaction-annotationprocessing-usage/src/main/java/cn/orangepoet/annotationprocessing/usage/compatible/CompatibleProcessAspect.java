package cn.orangepoet.annotationprocessing.usage.compatible;

import cn.orangepoet.annotationprocessing.processor.compatible.CompatibleProcessor;
import lombok.NonNull;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class CompatibleProcessAspect {

    @Pointcut("execution(public * cn.orangepoet.annotationprocessing.usage.compatible.FruitService.listFruit())")
    public void listFruit() {
    }

    @AfterReturning(value = "listFruit()", returning = "data")
    public void compatibleProcess(JoinPoint joinPoint, List<Fruit> data) {
        CompatibleProcessor processor = getProcessorBeanByName("fruitListCompatibleProcessor");
        processor.process(data);
    }

    @NonNull
    private CompatibleProcessor getProcessorBeanByName(String beanName) {
        return new FruitListCompatibleProcessor();
    }
}
