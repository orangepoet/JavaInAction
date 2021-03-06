package cn.orangepoet.annotationprocessing.usage.compatible.dispatcher;

import cn.orangepoet.annotationprocessing.processor.compatible.VersionRoute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@VersionRoute(value = "2.0", serviceType = GreetService.class)
public class GreetServiceImpl2_0 implements GreetService {

    @Override
    public String getName() {
        return "service 2.0";
    }

    @Override
    public void greet(String words) {
        log.info("{}: {}", getName(), words);
    }
}
