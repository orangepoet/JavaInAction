package cn.orangepoet.inaction.app.eventbus;

import java.io.IOException;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * 本示例主要演示Guava的EventBus如何使用
 *
 * @author chengzhi
 * @date 2020/05/29
 */
@Slf4j
public class Application {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();

        EventListener listener = new EventListener();
        eventBus.register(listener);

        eventBus.post("hello, world");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class EventListener {
        private static int eventHandled;

        @Subscribe
        public void stringEvent(String event) {
            log.info("EventListener: " + event);
            eventHandled++;
        }
    }
}
