package cn.orangepoet.inaction.spring.base.aspect.traffic;

import cn.orangepoet.inaction.spring.base.aspect.traffic.impl.TrafficFactory;
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
    private final Logger logger = LoggerFactory.getLogger(Traveler.class);

    @Autowired
    private TrafficFactory factory;
    //@Autowired
    //private List<String> myList;

    public void go(Traffic.TrafficWay way) {
        if (Optional.ofNullable(way).orElse(Traffic.TrafficWay.UNKNOWN) == Traffic.TrafficWay.UNKNOWN) {
            logger.error("empty way");
            return;
        }

        //myList.add("myname");
        Traffic traffic = factory.getTraffic(way);
        traffic.go();
    }
}
