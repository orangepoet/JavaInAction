package cn.orangepoet.inaction.parallel.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * @author chengzhi
 * @date 2019/09/14
 */
public class MutipleThread {
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(4);

    private static Logger log = LoggerFactory.getLogger(MutipleThread.class);
    private static ThreadLocal<Map<Integer, String>> mapThreadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            map.put(i, arg);
        }
        mapThreadLocal.set(map);

        Task<Integer> task1 = new Task<>(() -> {
            log.info("task1: {}", mapThreadLocal.get());
            Thread.sleep(2000);
            return 1;
        });
        Task<Integer> task2 = new Task<>(() -> {
            log.info("task2: {}", mapThreadLocal.get());
            Thread.sleep(2000);
            return 2;
        });
        Task<Integer> task3 = new Task<>(() -> {
            log.info("task3: {}", mapThreadLocal.get());
            Thread.sleep(2000);
            return 3;
        });

        List<Task> tasks = Arrays.asList(task1, task2, task3);
        List<Callable<Integer>> callables = new ArrayList<>();
        //tasks.stream().map(x -> x.getCallable()).collect(toList());
        tasks.stream().forEach(t -> callables.add(t.getCallable()));
        TaskUtils.run(callables, 4);

        Integer task1Result = task1.getResult();
        log.info("task1 result: " + task1Result);

        try {
            EXECUTOR_SERVICE.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Task<T> {
        private final Callable<T> callable;

        private T result;

        public Task(Callable<T> callable) {
            this.callable = callable;
        }

        public void run() {
            try {
                result = callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Callable<T> getCallable() {
            return callable;
        }

        public T getResult() {
            return result;
        }
    }

    private static class TaskUtils {

        public TaskUtils() {
        }

        public static void run(List<Runnable> runnables) {
            for (Runnable runnable : runnables) {
                EXECUTOR_SERVICE.execute(runnable);
            }
        }

        public static <T> void run(List<Callable<T>> callables, int maxParallel) {
            final ForkJoinPool forkJoinPool0 = new ForkJoinPool(maxParallel);
            forkJoinPool0.invokeAll(callables);
        }
    }
}
