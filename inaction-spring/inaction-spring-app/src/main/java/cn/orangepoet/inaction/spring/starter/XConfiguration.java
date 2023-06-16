package cn.orangepoet.inaction.spring.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chengzhi
 * @date 2021/12/20
 */
@Configuration
public class XConfiguration {
    @Bean
    public Action stop() {
        return new Stop();
    }
}
