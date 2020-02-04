package cn.orangepoet.annotationprocessing.processor.concrete;

import cn.orangepoet.annotationprocessing.processor.compatible.ServiceVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ServiceVersion(type = MyHandler.class, floor = "2.0")
public class MyHandlerImpl2_0 implements MyHandler {

    @Override
    public String getName() {
        return "service 2.0";
    }

    @Override
    public void sayHi() {
        log.info("hi, " + getName());
    }
}
