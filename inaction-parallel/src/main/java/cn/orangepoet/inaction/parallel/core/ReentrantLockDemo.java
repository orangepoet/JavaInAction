package cn.orangepoet.inaction.parallel.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private static ExecutorService service = Executors.newFixedThreadPool(5);

    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            service.submit(new MyTask());
        }

        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class MyTask implements Runnable {
        private static final ReentrantLock LOCK = new ReentrantLock(true);

        @Override
        public void run() {
            LOCK.lock();
            try {
                try {
                    System.out.println("handling");
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                LOCK.unlock();
            }
        }
    }
}
