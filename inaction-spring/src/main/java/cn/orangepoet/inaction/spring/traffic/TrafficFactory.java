package cn.orangepoet.inaction.spring.traffic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by Orange on 2017/1/11.
 */
@Component
public class TrafficFactory {
    @Autowired
    @Qualifier("flightWay")
    private Traffic flightWay;

    @Autowired
    @Qualifier("trainWay")
    private Traffic trainWay;

    public Traffic getTraffic(TrafficWay way) {
        switch (way) {
            case FLIGHT_WAY:
                return flightWay;
            case TRAIN_WAY:
                return trainWay;
            default:
                throw new IllegalArgumentException("way is not recognized!");
        }
    }
}
