package cn.orangepoet.annotationprocessing.processor.concrete;

import cn.orangepoet.annotationprocessing.processor.compatible.ServiceVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ServiceVersion(type = MyHandler.class, floor = "1.0")
public class MyHandlerImpl1_0 implements MyHandler {
    @Override
    public String getName() {
        return "service 1.0";
    }

    @Override
    public void sayHi() {
        log.info("hi, " + getName());
    }
}
