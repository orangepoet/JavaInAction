package cn.orangepoet.inaction.spring.base.aspect.sample.traffic;

import java.util.List;

import cn.orangepoet.inaction.spring.base.aspect.sample.traffic.impl.Train;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by Orange on 2017/1/11.
 */
@Component
public class TrafficFactory {

    private String name;

    //@Autowired
    //@Qualifier("trainWay")
    private Traffic train;

    @Autowired
    public void setTrain(Train train) {
        this.train = train;
    }

    @Autowired
    @Qualifier("flightWay")
    private Traffic flightWay;


    @Autowired
    @Getter
    private List<Traffic> trafficList;

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
