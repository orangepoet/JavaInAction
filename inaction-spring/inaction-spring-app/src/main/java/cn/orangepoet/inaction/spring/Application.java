package cn.orangepoet.inaction.spring;

import cn.orangepoet.inaction.spring.cache.RemoteClient;
import cn.orangepoet.inaction.spring.cache.UserClient;
import cn.orangepoet.inaction.spring.event.CustomSpringEventPublisher;
import cn.orangepoet.inaction.spring.lifecycle.MyBean;
import cn.orangepoet.inaction.spring.starter.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(CustomProperties.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    /**
     * 依赖starter/MyConfiguration中的自动装配，也可以通过本地bean声明覆盖默认Bean配置。
     *
     * @param command
     * @return
     */
//    @Bean
    public CommandLineRunner starter(Command command) {
        return args -> command.doAction();
    }

    /**
     * 自定义属性
     *
     * @param customProperties
     * @return
     */
//    @Bean
    public CommandLineRunner properties(CustomProperties customProperties) {
        return args -> log.info("name->{}", customProperties.getName());
    }

    /**
     * Bean的生命周期
     *
     * @param myBean
     * @return
     */
//    @Bean
    public CommandLineRunner lifeCycle(MyBean myBean) {
        return args -> log.info("myBean->{}", myBean);
    }

    /**
     * Spring Cache的切换
     *
     * @param remoteClient
     * @param userClient
     * @return
     */
    //    @Bean
    public CommandLineRunner switchCache(RemoteClient remoteClient, UserClient userClient) {
        return (args) -> {
            for (int i = 0; i < 3; i++) {
                log.info("remoteClient.getData..., times: {}", i);
                String data = remoteClient.getData(String.valueOf(1));
                log.info("data: {}", data);
            }

            for (int i = 0; i < 3; i++) {
                log.info("userClient.getUser1..., times: {}", i);
                String user1 = userClient.getUser1(1);
                log.info("user1: {}", user1);
            }
        };
    }

    /**
     * Spring Event, 发布事件
     *
     * @param publisher
     * @return
     */
//    @Bean
    public CommandLineRunner publishEvent(CustomSpringEventPublisher publisher) {
        return args -> publisher.doStuffAndPublishEvent("hello, world");
    }
}
