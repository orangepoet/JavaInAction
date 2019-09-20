package cn.orangepoet.inaction.spring.event.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author chengzhi
 * @date 2019/09/20
 */
@Slf4j
@Component
public class CustomSpringEventListener2 implements ApplicationListener<CustomSpringEvent> {
    @Override
    public void onApplicationEvent(CustomSpringEvent event) {
        log.info("Received spring custom event - " + event.getMessage());
    }
}
