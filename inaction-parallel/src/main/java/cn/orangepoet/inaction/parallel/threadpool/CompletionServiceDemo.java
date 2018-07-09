package cn.orangepoet.inaction.parallel.threadpool;

import java.util.Random;
import java.util.concurrent.*;


/**
 * 使用ExecutorService示例
 */
public class CompletionServiceDemo {
    private static final int THREAD_COUNT = 5;
    private static ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
    private static final Random rnd = new Random();

    public static void main(String[] args) {
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);
        for (int i = 0; i < 10; i++) {
            final int j = i;
            completionService.submit(
                    () -> {
                        System.out.println("calculate begins, seq:" + String.valueOf(j));
                        int result = rnd.nextInt(1000);
                        Thread.sleep(result * 15);
                        System.out.println("calculate finish, seq:" + String.valueOf(j) + " result: " + result);
                        return result;
                    }
            );
        }

        int total = 0;
        for (int i = 0; i < 10; i++) {
            try {
                Future<Integer> future = completionService.take();
                total += future.get();
                System.out.println("total update, value: " + total);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        System.out.println("over");
    }
}
