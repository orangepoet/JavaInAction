package cn.orangepoet.inaction.parallel;

import java.util.Random;
import java.util.concurrent.*;


/**
 * 使用ExecutorService样例
 */
public class ExecutorDemo {
    private static final int THREAD_COUNT = 5;
    private static ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
    private static final Random rnd = new Random();

    public static void main(String[] args) {
//        Runnable task = () -> {
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("hello, world");
//        };
//        Callable<List<Integer>> calculates = () -> {
//            List<Integer> result = new ArrayList<>();
//            for (int i = 0; i < 10; i++) {
//                System.out.println("calculate begins, seq:" + String.valueOf(i));
//                Thread.sleep(2000);
//                result.add(rnd.nextInt(1000));
//                System.out.println("calculate end, seq:" + String.valueOf(i));
//            }
//            return result;
//        };


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
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

//        Future<List<Integer>> future = executor.submit(calculates);
//        try {
//            System.out.println("future.get()");
//            List<Integer> result = future.get();
//            int total = result.stream().mapToInt(n -> n).sum();
//            System.out.println("future.get(), result is " + String.valueOf(total));
//            System.out.println(total);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

//        Callable<Integer> task2 = () -> 1;
//        Future<Integer> future = executor.submit(task2);
//        try {
//            future.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        executor.shutdown();
        System.out.println("over");
    }
}
