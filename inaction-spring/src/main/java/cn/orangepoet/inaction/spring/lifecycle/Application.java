package cn.orangepoet.inaction.spring.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Bean的生命周期：
 *
 * <p>
 * ctor -> setter injection -> PreInitialization(BeanPostProcessor) -> @PostConstruct -> afterPropertiesSet
 * -> init-method -> PostInitialization(BeanPostProcessor)
 * </p>
 *
 * @author chengzhi
 * @date 2019/12/03
 */
@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private FooBean fooBean;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... strings) throws Exception {
        log.info("run");
    }
}
