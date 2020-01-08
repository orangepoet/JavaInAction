package cn.orangepoet.inaction.spring.aspect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author chengzhi
 * @date 2020/01/02
 */
@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private SubjectImpl subject;

    //private Executor executor = Executors.newFixedThreadPool(2, r -> new Thread("myThread"));
    private Executor executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... strings) throws Exception {
        List<Integer> lst = new ArrayList<>();
        for (int i = 1; i <=8; i++) {
            lst.add(i);
        }

        executor.execute(() -> {
            log.info("execute1 start...");
            List<Integer> result = new ArrayList<>();
            lst.parallelStream()
                .map(i -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int num = i * 10;
                    log.info("num1: " + num);
                    return num;
                })
                .forEachOrdered(result::add);
            log.info("result1: " + result);
        });

        executor.execute(() -> {
            log.info("execute2 start...");
            List<Integer> result = new ArrayList<>();
            lst.parallelStream()
                .map(i -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int num = i * 2;
                    log.info("num2: " + num);
                    return num;
                })
                .forEachOrdered(result::add);
            log.info("result2: " + result);
        });
    }
}
