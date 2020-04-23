package cn.orangepoet.inaction.parallel.executorservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * ForkJoin, 采用的是分治算法, 将任务的集合分解为更小的子集计算, 然后再合并各个子集的结果;
 *
 * 调用的主要方法是fork(), join()方法, 分别对应递归任务分解, 和任务结果的获取(同步阻塞等待);
 */
public class ForkJoinDemo {
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(8);
    private static final CompletionService<Integer> COMPLETION_SERVICE = new ExecutorCompletionService<>(EXECUTOR_SERVICE);

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());

        List<LongTimeCompute> longTimeComputes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            longTimeComputes.add(new LongTimeCompute());
        }

        // 1: simple loop
        long start = System.currentTimeMillis();
        int total0 = 0;
        for (LongTimeCompute longTimeCompute : longTimeComputes) {
            int number = longTimeCompute.getNumber();
            total0 += number;
        }
        long end = System.currentTimeMillis();
        System.out.println("system0 elapse time: " + (end - start));

        //  2: completion service
        int total1 = 0;
        start = System.currentTimeMillis();
        for (LongTimeCompute longTimeCompute : longTimeComputes) {
            COMPLETION_SERVICE.submit(() -> longTimeCompute.getNumber());
        }
        for (int i = 0; i < longTimeComputes.size(); i++) {
            try {
                Future<Integer> future = COMPLETION_SERVICE.take();
                Integer result = future.get();
                total1 += result;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        end = System.currentTimeMillis();
        System.out.println("system1 elapse time: " + (end - start));


        // 3: fork join
        start = System.currentTimeMillis();
        int total2 = new Computer().sumList(longTimeComputes);
        end = System.currentTimeMillis();
        System.out.println("system2 elapse time: " + (end - start));

        // 4: stream
        start = System.currentTimeMillis();
        Integer total3 = longTimeComputes.stream().parallel().map(c -> c.getNumber()).reduce(0, Integer::sum);
        end = System.currentTimeMillis();
        System.out.println("system3 elapse time: " + (end - start));

        assert total0 == total1 && total1 == total2 && total2 == total3;

        EXECUTOR_SERVICE.shutdown();
    }

    private static class LongTimeCompute {
        private static final Random rnd = new Random();
        private final int num;

        public LongTimeCompute() {
            num = rnd.nextInt(100);
        }

        public int getNumber() {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return this.num;
        }
    }

    private static class Computer {
        private static final ForkJoinPool forkJoinPool = new ForkJoinPool(8);

        public int sumArray(int[] intArr) {
            ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(new SumTask(intArr));
            try {
                return forkJoinTask.get();
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }

        public int sumList(List<LongTimeCompute> longTimeComputes) {
            ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(new SumListTask(longTimeComputes));

            try {
                return forkJoinTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

    private static class SumListTask extends RecursiveTask<Integer> {
        private final List<LongTimeCompute> longTimeComputes;

        private static final int THRESHOLD = 10;

        public SumListTask(List<LongTimeCompute> longTimeComputes) {
            this.longTimeComputes = longTimeComputes;
        }

        @Override
        protected Integer compute() {
            if (longTimeComputes == null || longTimeComputes.size() == 0) {
                return 0;
            }

            if (longTimeComputes.size() <= THRESHOLD) {
                int sumVal = 0;
                for (int i = 0; i < longTimeComputes.size(); i++) {
                    sumVal += longTimeComputes.get(i).getNumber();
                }
                return sumVal;
            }

            int mid = longTimeComputes.size() / 2;
            List<LongTimeCompute> leftPart = this.longTimeComputes.subList(0, mid);
            List<LongTimeCompute> rightPart = this.longTimeComputes.subList(mid, this.longTimeComputes.size());

            ForkJoinTask<Integer> leftFork = new SumListTask(leftPart).fork();
            ForkJoinTask<Integer> rightFork = new SumListTask(rightPart).fork();

            Integer leftResult = leftFork.join();
            Integer rightResult = rightFork.join();
            return leftResult + rightResult;
        }
    }

    private static class SumTask extends RecursiveTask<Integer> {
        private final int[] intArr;
        private static final int THRESHOLD = 2;

        public SumTask(int[] intArr) {
            this.intArr = intArr;
        }

        @Override
        protected Integer compute() {
            if (intArr == null || intArr.length == 0) {
                return 0;
            }

            if (intArr.length < THRESHOLD) {
                int sumVal = 0;
                for (int i = 0; i < intArr.length; i++) {
                    sumVal += intArr[i];
                }
                return sumVal;
            }

            int mid = intArr.length / 2;
            int[] leftArr = Arrays.copyOfRange(intArr, 0, mid);
            int[] rightArr = Arrays.copyOfRange(intArr, mid, intArr.length);


            ForkJoinTask<Integer> leftFork = new SumTask(leftArr).fork();
            ForkJoinTask<Integer> rightFork = new SumTask(rightArr).fork();

            Integer leftResult = leftFork.join();
            Integer rightResult = rightFork.join();
            return leftResult + rightResult;
        }
    }
}
