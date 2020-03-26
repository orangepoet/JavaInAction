package cn.orangepoet.inaction.app.googleauto.service;

import java.util.Iterator;
import java.util.ServiceLoader;

public class Application {
    public static void main(String[] args) {
        Iterator<MyService> iterator = ServiceLoader.load(MyService.class).iterator();
        String service = iterator.next().service();
        System.out.println(service);
    }
}
