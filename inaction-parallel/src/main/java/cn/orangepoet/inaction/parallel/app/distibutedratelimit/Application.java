package cn.orangepoet.inaction.parallel.app.distibutedratelimit;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

    private static ScheduleExecutor scheduler = new RateSchedulerImpl("test", 5);
    private static final int BATCH_COUNT = 10;
    private static final int PARALLEL = 8;
    private static CountDownLatch startGate = new CountDownLatch(1);
    private static CountDownLatch endGate = new CountDownLatch(PARALLEL);

    private static ExecutorService executorService = Executors.newFixedThreadPool(PARALLEL);

    public static void main(String[] args) {
        System.out.println("hello, world");

        scheduler.schedule();
        for (int j = 0; j < PARALLEL; j++) {
            final int x = j;
            executorService.submit(() -> {
                try {
                    startGate.await();
                    System.out.println("start task");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 1; i <= BATCH_COUNT; i++) {
                    final int k = i + BATCH_COUNT * x;
                    scheduler.execute(() -> {
                        System.out.println(String.valueOf(k));
                        //startGate.countDown();
                    });
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                endGate.countDown();
            });
        }
        try {
            startGate.countDown();
            endGate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finish");
    }
}
