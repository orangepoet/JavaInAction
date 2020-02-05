package cn.orangepoet.annotationprocessing.usage;

import cn.orangepoet.annotationprocessing.processor.compatible.VersionContext;
import cn.orangepoet.annotationprocessing.usage.compatible.MyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author chengzhi
 * @date 2020/01/20
 */

@SpringBootApplication
public class Application {

    @Autowired
    private MyHandler myHandler;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner commandLineRunner2() {
        return (args) -> {
            PersonBuilder personBuilder = new PersonBuilder();
            Person orange = personBuilder.setAge(12).setName("orange").build();
            System.out.println(orange);
        };
    }

    @Bean
    public CommandLineRunner CommandLineRunner1() {
        return (args) -> {
            VersionContext.setCurrentVersion("2.0");
            myHandler.greet("orange", "hello, world");
        };
    }
}
