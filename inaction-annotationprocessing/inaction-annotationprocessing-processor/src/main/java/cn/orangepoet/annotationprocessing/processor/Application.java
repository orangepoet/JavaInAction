package cn.orangepoet.annotationprocessing.processor;

import cn.orangepoet.annotationprocessing.processor.concrete.MyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private MyHandler myHandler;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... strings) throws Exception {
        for (int i = 0; i < 3; i++) {
            log.info(myHandler.getName());
        }
    }
}
