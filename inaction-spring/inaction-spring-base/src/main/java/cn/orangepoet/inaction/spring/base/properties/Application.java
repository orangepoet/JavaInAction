package cn.orangepoet.inaction.spring.base.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author chengzhi
 * @date 2019/12/11
 */
@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(CustomProperties.class)
public class Application {
    @Autowired
    private Tools tools;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return (args) -> {
            tools.showName();
            System.out.println(tools.getI());
        };
    }
}
