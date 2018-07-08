package cn.orangepoet.inaction.spring.traffic.impl;

import cn.orangepoet.inaction.spring.traffic.Traffic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Orange on 2017/1/11.
 */
@Component("flightWay")
public class Flight implements Traffic {
    private Logger logger = LoggerFactory.getLogger(Flight.class);

    @Override
    public void go() {
        logger.info("go home by flight M2017 上海-南昌");
    }
}
