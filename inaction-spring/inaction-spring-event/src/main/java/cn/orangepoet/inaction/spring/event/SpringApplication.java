package cn.orangepoet.inaction.spring.event;

import cn.orangepoet.inaction.spring.event.custom.CustomSpringEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chengzhi
 * @date 2019/09/20
 */
@Slf4j
@SpringBootApplication
public class SpringApplication implements CommandLineRunner {
    @Autowired
    private CustomSpringEventPublisher publisher;

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class);
    }

    @Override
    public void run(String... strings) throws Exception {
        publisher.doStuffAndPublishEvent("hello, world");
    }
}
