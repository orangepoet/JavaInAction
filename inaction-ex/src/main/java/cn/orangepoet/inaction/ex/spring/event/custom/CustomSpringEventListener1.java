package cn.orangepoet.inaction.ex.spring.event.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author chengzhi
 * @date 2019/09/20
 */
@Slf4j
@Component
public class CustomSpringEventListener1 implements ApplicationListener<CustomSpringEvent> {
    @Override
    public void onApplicationEvent(CustomSpringEvent event) {
        log.info("CustomSpringEventListener1 Received spring custom event - " + event.getMessage());
        log.info("threadId: " + Thread.currentThread().getId());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("failed");
    }
}
