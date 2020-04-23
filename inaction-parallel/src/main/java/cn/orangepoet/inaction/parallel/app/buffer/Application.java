package cn.orangepoet.inaction.parallel.app.buffer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
    private static final ExecutorService SERVICE = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        Buffer<String> buffer = new ConditionBoundedBuffer<>(2);

        CountDownLatch endGate = new CountDownLatch(1);

        SERVICE.submit(() -> {
            try {
                for (int i = 0; ; i++) {
                    final String str = String.valueOf(i);
                    buffer.put(str);
                    System.out.println("produce: " + str);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        SERVICE.submit(() -> {
            for (; ; ) {
                try {
                    String str = buffer.take();
                    System.out.println("consume: " + str);
                    Thread.sleep(33);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            endGate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("over");
    }
}
