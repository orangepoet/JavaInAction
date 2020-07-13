package cn.orangepoet.inaction.spring.base.aspect.sample.traffic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <p>
 * Auto wired learning, scan package config in spring.xml, add service annotation to
 * mark the class managed by spring container.
 * </p>
 * Created by Orange on 2017/1/11.
 */
@Component
public class Traveler {
    private Logger logger = LoggerFactory.getLogger(Traveler.class);

    @Autowired
    private TrafficFactory factory;
    //@Autowired
    //private List<String> myList;

    public void go(TrafficWay way) {
        if (Optional.ofNullable(way).orElse(TrafficWay.UNKNOWN) == TrafficWay.UNKNOWN) {
            logger.error("empty way");
        }

        //myList.add("myname");
        Traffic traffic = factory.getTraffic(way);
        traffic.go();
    }
}
