package cn.orangepoet.inaction.spring.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomSpringEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public CustomSpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void doStuffAndPublishEvent(String message) {
        CustomSpringEvent event = new CustomSpringEvent(this, message);
        applicationEventPublisher.publishEvent(event);
        log.info("threadId: " + Thread.currentThread().getId());
    }
}
