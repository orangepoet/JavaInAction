package cn.orangepoet.annotationprocessing.usage.compatible;

import cn.orangepoet.annotationprocessing.processor.compatible.ServiceVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ServiceVersion(serviceType = MyHandler.class, floor = "1.0")
public class MyHandlerImpl1_0 implements MyHandler {
    @Override
    public String getName() {
        return "service 1.0";
    }

    @Override
    public void greet(String greeter, CharSequence words) {
        log.info("{}: {}", greeter, words);
    }
}



