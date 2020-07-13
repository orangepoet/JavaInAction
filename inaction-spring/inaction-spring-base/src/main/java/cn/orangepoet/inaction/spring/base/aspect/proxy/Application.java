package cn.orangepoet.inaction.spring.base.aspect.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author chengzhi
 * @date 2020/05/03
 */
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Autowired
    private ProxySelfMethod proxySelfMethod;

    @Override
    public void run(String... args) throws Exception {
        proxySelfMethod.method1();
        //proxySelfMethod.method2();
    }
}
