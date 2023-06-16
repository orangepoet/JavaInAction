package cn.orangepoet.inaction.spring.event;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * @author chengzhi
 * @date 2019/09/20
 */
@Configuration
public class AsynchronousSpringEventsConfig {
    //@Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();

        // 为每个观察者启动一个线程用作通知
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        eventMulticaster.setTaskExecutor(taskExecutor);
        return eventMulticaster;
    }
}