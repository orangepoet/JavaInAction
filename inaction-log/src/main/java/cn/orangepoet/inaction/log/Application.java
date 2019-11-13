package cn.orangepoet.inaction.log;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 1. MDC使用, 提供给全链路跟踪系统一个traceid, 使用切面aop方式在每个入口处设置一个,
 *    和以往类似, 可以在
 * 2. 分模块输入日志文件, 设置logger.name为对应的包名或者文件名, 关联不同的appender
 * 3.
 */
@SpringBootApplication
public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }


    @Bean
    public CommandLineRunner lineRunner() {
        return (String... args) -> {
            MDC.put("mdc_trace_id","lineRunner");
            logger.error("i'm lineRunner" + Common.sayHi());
        };
    }

    @Bean
    public CommandLineRunner lineRunner2() {
        return (String... args) -> {
            MDC.put("mdc_trace_id","lineRunner2");
            logger.error("i'm lineRunner2");
        };
    }

    @Bean
    public CommandLineRunner lineRunner3() {
        return (String... args) -> {
            //MDC.put("mdc_trace_id","lineRunner2");
            try {
                doSmth();
            } catch (Exception e) {
                logger.error(ExceptionUtils.getStackTrace(e));
            }
            //logger.error("i'm lineRunner2");
        };
    }

    private void doSmth() {
        throw new IllegalArgumentException("name is invalid");
    }

    //    /**
//     * 配置List bean, 使用CopyOnWriteList
//     *
//     * @return
//     */
//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public List<String> myList() {
//        return new CopyOnWriteArrayList<>();
//    }
}
