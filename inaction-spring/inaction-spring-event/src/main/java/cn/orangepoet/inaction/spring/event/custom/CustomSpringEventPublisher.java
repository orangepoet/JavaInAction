package cn.orangepoet.inaction.spring.event.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author chengzhi
 * @date 2019/09/20
 */
@Slf4j
@Component
public class CustomSpringEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void doStuffAndPublishEvent(String message) {
        CustomSpringEvent event = new CustomSpringEvent(this, message);
        applicationEventPublisher.publishEvent(event);
        log.info("threadId: " + Thread.currentThread().getId());
    }
}
