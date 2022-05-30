package cn.orangepoet.inaction.spring.base.eg;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sound.sampled.Line;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chengzhi
 * @date 2022/05/30
 */
@SpringBootApplication
@Slf4j
public class Application2 {
    public static void main(String[] args) {
        SpringApplication.run(Application2.class);
    }

    @Resource
    private MyBean myBean;

    //@Resource
    //private MyBean myBean1;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return strings -> {
            log.info("mybean: {}", myBean);
            //log.info("mybean1: {}", myBean1);
        };
    }

    @Bean(initMethod = "setVersion")
    @Scope("prototype")
    public MyBean myBean(String f3) {
        MyBean myBean1 = new MyBean("1", "2");
        myBean1.setF3(f3);
        return myBean1;
    }

    @Bean
    public String f3() {
        return "f3";
    }

    @Data
    @Slf4j
    static class MyBean {
        public static final AtomicInteger versions = new AtomicInteger();

        public MyBean(String f1, String f2) {
            log.info("call constructor");
            this.f1 = f1;
            this.f2 = f2;
        }

        @PostConstruct
        public void init() {
            log.info("call PostConstruct");
        }

        private final String f1;
        private final String f2;

        private String f3;

        public void setF3(String f3) {
            log.info("call setProperties");
            this.f3 = f3;
        }

        private int version;

        public void setVersion() {
            log.info("call init-method");
            this.version = versions.incrementAndGet();
        }
    }
}
