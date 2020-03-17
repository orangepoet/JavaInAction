package cn.orangepoet.inaction.spring.deps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chengzhi
 * @date 2020/03/17
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private Greeter greeter;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... args) throws Exception {
        greeter.greet();
    }
}
