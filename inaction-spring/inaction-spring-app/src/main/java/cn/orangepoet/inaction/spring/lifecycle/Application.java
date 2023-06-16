package cn.orangepoet.inaction.spring.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @author chengzhi
 * @date 2022/05/30
 */
@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Resource
    private MyBean myBean;

    @Override
    public void run(String... strings) {
        log.info("mybean: {}", myBean);
    }
}
