package cn.orangepoet.inaction.spring.lifecycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author chengzhi
 * @date 2022/06/01
 */
@Configuration
public class MyConfiguration {
    @Bean
    public String f3() {
        return "f3";
    }

    @Bean(initMethod = "setVersion")
    @Scope("prototype")
    public MyBean myBean(String f3) {
        MyBean myBean1 = new MyBean("1", "2");
        myBean1.setF3(f3);
        return myBean1;
    }
}
