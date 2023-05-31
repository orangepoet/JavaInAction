package cn.orangepoet.inaction.ex.spring.starter;

import cn.orangepoet.inaction.spring.starter.my.Action;
import cn.orangepoet.inaction.spring.starter.my.Stop;
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
