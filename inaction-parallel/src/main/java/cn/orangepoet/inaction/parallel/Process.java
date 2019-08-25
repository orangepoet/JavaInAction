package cn.orangepoet.inaction.parallel;

import cn.orangepoet.inaction.parallel.example.SchedulerTask;

public class Process {
    public static void main(String[] args) {
        new SchedulerTask().execute();
    }
}
