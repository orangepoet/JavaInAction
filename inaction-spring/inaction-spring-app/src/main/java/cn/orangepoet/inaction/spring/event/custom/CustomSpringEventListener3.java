package cn.orangepoet.inaction.spring.event.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author chengzhi
 * @date 2019/09/20
 */
@Slf4j
@Component
public class CustomSpringEventListener3 {

    @Async
    @EventListener
    public void handleContextStart(CustomSpringEvent event) {
        log.info("CustomSpringEventListener3 Received spring custom event - " + event.getMessage());
        log.info("threadId: " + Thread.currentThread().getId());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //throw new RuntimeException("failed");
    }
}
