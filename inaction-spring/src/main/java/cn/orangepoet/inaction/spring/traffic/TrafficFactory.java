package cn.orangepoet.inaction.spring.traffic;

import cn.orangepoet.inaction.spring.traffic.impl.Flight;
import cn.orangepoet.inaction.spring.traffic.impl.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Orange on 2017/1/11.
 */
@Component
public class TrafficFactory {

    @Value("${name}")
    private String name;

    @Autowired
    private Flight train;

    @Autowired
    private Train flightWay;

    public Traffic getTraffic(TrafficWay way) {
        System.out.println(name);
        switch (way) {
            case FLIGHT_WAY:
                return flightWay;
            case TRAIN_WAY:
                return train;
            default:
                throw new IllegalArgumentException("way is not recognized!");
        }
    }
}
