package cn.orangepoet.inaction.spring.event;

import cn.orangepoet.inaction.spring.event.custom.CustomSpringEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author chengzhi
 * @date 2019/09/20
 */
@Slf4j
@SpringBootApplication
public class Application {
    @Autowired
    private CustomSpringEventPublisher publisher;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner runner() {
        return args -> publisher.doStuffAndPublishEvent("hello, world");
    }
}
