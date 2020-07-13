package cn.orangepoet.inaction.spring.base.aspect.sample.traffic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chengzhi
 * @date 2020/01/02
 */
@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private Traveler traveler;
    @Autowired
    private TrafficFactory factory;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... strings) throws Exception {
        traveler.go(TrafficWay.TRAIN_WAY);

        factory.getTrafficList().forEach(traffic -> {
            log.info(traffic.toString());
        });
    }
}
