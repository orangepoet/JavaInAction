package cn.orangepoet.inaction.spring.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Orange on 2017/1/11.
 */
public class SpringContainer {
    private static AbstractApplicationContext ctx;

    private SpringContainer() {
    }

    static {
        try {
            ctx = new ClassPathXmlApplicationContext("spring.xml");
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    public static <T> T getBean(Class clazz) {
        return (T) ctx.getBean(clazz);
    }

    public static <T> T getBean(String id) {
        return (T) ctx.getBean(id);
    }
}
