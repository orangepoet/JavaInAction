package cn.orangepoet.annotationprocessing.usage;

import cn.orangepoet.annotationprocessing.processor.compatible.VersionContext;
import cn.orangepoet.annotationprocessing.usage.compatible.Fruit;
import cn.orangepoet.annotationprocessing.usage.compatible.FruitService;
import cn.orangepoet.annotationprocessing.usage.compatible.MyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author chengzhi
 * @date 2020/01/20
 */

@Slf4j
@SpringBootApplication
public class Application {

    @Autowired
    private MyHandler myHandler;

    @Autowired
    private FruitService fruitService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    //    @Bean
    public CommandLineRunner commandLineRunner2() {
        return (args) -> {
            PersonBuilder personBuilder = new PersonBuilder();
            Person orange = personBuilder.setAge(12).setName("orange").build();
            System.out.println(orange);
        };
    }

    //    @Bean
    public CommandLineRunner CommandLineRunner1() {
        return (args) -> {
            VersionContext.setCurrentVersion("3.0");
            log.info("current version: 3.0");
            myHandler.greet(myHandler.getName(), "hello, world");

            VersionContext.setCurrentVersion("2.0");
            log.info("current version: 2.0");
            myHandler.greet(myHandler.getName(), "hello, world");

            VersionContext.setCurrentVersion("1.0");
            log.info("current version: 1.0");
            myHandler.greet(myHandler.getName(), "hello, world");
        };
    }

    @Bean
    public CommandLineRunner commandLineRunner3() {
        return (args) -> {
            printResult("4.0");
            printResult("3.0");
            printResult("2.0");
            printResult("1.0");
        };
    }

    private void printResult(String currentVersion) {
        log.info("current version: " + currentVersion);
        VersionContext.setCurrentVersion(currentVersion);
        Fruit fruit = fruitService.showFruit();
        log.info(fruit.toString());
        List<Fruit> fruits = fruitService.listFruit();
        log.info(fruits.toString());
    }
}
