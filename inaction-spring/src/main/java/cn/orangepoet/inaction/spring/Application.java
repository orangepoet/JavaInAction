package cn.orangepoet.inaction.spring;

import cn.orangepoet.inaction.spring.traffic.TrafficWay;
import cn.orangepoet.inaction.spring.traffic.Traveler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@SpringBootApplication
public class Application {
    @Autowired
    private Traveler traveler;


    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }


    @Bean
    public CommandLineRunner lineRunner2() {
        return (String... args) -> {
            traveler.go(TrafficWay.FLIGHT_WAY);
        };
    }

    /**
     * 配置List bean, 使用CopyOnWriteList
     *
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public List<String> myList() {
        return new CopyOnWriteArrayList<>();
    }
}
