package cn.orangepoet.inaction.spring.base.aspect.traffic.impl;

import cn.orangepoet.inaction.spring.base.aspect.traffic.Traffic;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by Orange on 2017/1/11.
 */
@Component("flightWay")
@ToString
@Order(1)
public class Flight implements Traffic {
    private final Logger logger = LoggerFactory.getLogger(Flight.class);

    @Override
    public void go() {
        logger.info("go home by flight M2017 上海-南昌");
    }
}
