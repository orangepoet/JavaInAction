package cn.orangepoet.inaction.spring.samples;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author chengzhi
 * @date 2020/03/25
 */
@SpringBootApplication
public class Application {

    @Autowired
    private List<FetchData> fetchDataList = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return (args) -> {
            //fetchDataList.forEach(fetch -> {
            //    DBData data = fetch.fetch();
            //    System.out.println(data);
            //});
            List<String> lst = new ArrayList<>();
            lst.add("a");
            lst.add("b");
            Iterator<String> iterator = lst.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                iterator.remove();
            }
        };
    }
}
