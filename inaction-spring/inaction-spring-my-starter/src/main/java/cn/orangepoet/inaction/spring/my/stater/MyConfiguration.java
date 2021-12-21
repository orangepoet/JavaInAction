package cn.orangepoet.inaction.spring.my.stater;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chengzhi
 * @date 2021/12/20
 */
@Configuration
public class MyConfiguration {

    @Bean
    //@ConditionalOnMissingBean(name = "move")
    @ConditionalOnMissingBean
    public Action move() {
        return new Move();
    }

    @Bean
    @ConditionalOnMissingBean
    public Command command(Action move) {
        return new Command(move);
    }
}