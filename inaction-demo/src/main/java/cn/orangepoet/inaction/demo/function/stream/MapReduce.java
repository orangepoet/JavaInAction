package cn.orangepoet.inaction.demo.function.stream;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MapReduce {

    private static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();

    public static void main(String[] args) {
        List<Integer> lst = Arrays.asList(1, 2, 3);
        Integer total = lst.stream().map(i -> i * 10).reduce(0, Integer::sum);
        System.out.println(total);

        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    private static void forkJoinMethod() {
        RecursiveTask<Integer> task = new RecursiveTask<Integer>() {
            @Override
            protected Integer compute() {
                return null;
            }
        };
        FORK_JOIN_POOL.submit(task);
        //IntStream.range(0, 1000);
    }
}
