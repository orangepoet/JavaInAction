package cn.orangepoet.inaction.parallel.example;

import cn.orangepoet.inaction.parallel.example.SchedulerTask;

public class Application {
    public static void main(String[] args) {
        new SchedulerTask().execute();
    }
}
