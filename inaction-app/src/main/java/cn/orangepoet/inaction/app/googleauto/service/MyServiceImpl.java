package cn.orangepoet.inaction.app.googleauto.service;

import com.google.auto.service.AutoService;

@AutoService(MyService.class)
public class MyServiceImpl implements MyService {
    public String service() {
        return "hello, MyService";
    }
}
