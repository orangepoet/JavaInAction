package cn.orangepoet.inaction.spring.base.aspect.sample.traffic.impl;

import cn.orangepoet.inaction.spring.base.aspect.sample.traffic.Traffic;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by Orange on 2017/1/11.
 */
@Component("trainWay")
@ToString
@Order(2)
public class Train implements Traffic {
    private Logger logger = LoggerFactory.getLogger(Flight.class);

    @Override
    public void go() {
        logger.info("go home by train: Z88 上海-南昌");
    }
}
