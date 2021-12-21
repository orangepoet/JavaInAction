package cn.orangepoet.inaction.spring.base.starter.ussage;

import cn.orangepoet.inaction.spring.my.stater.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @author chengzhi
 * @date 2021/12/20
 */
@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Resource
    private Command command;

    @Override
    public void run(String... args) throws Exception {
        command.doAction();
    }
}
