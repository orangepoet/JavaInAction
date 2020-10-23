package cn.orangepoet.inaction.parallel.executorservice;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * 使用自定义的ForkJoinPool执行并行流, 注意如果使用的不是ForkJoinPool, ParallelStream不会用到
 *
 * @author chengzhi
 * @date 2020/01/02
 */
@Slf4j
public class CustomForkJoinPool {
    //private static final ExecutorService EXECUTOR_SERVICE = new ForkJoinPool(2);
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(8, r -> new Thread(r, "CustomThreadFactory"));

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> source = IntStream.range(1, 11).boxed().collect(toList());

        log.info("test1: {}", test1(source));
        log.info("test2: {}", test2(source));
        log.info("test3: {}", test3(source));
    }

    /**
     * 简单的并行流
     *
     * @param source
     * @return
     */
    private static List<Integer> test1(List<Integer> source) {
        return source.parallelStream().map(i -> {
                log.info("compute: {}", i);
                int num = i * 10;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("compute error", e);
                }
                return num;
            }
        ).collect(toList());
    }

    /**
     * 使用CompletableFuture.supplyAsync 让自定义线程池参与工作
     *
     * @param source
     * @return
     */
    private static List<Integer> test2(List<Integer> source) {
        List<Integer> result = new ArrayList<>();
        source.parallelStream()
            .map(x -> CompletableFuture.supplyAsync(() -> {
                    log.info("compute: {}", x);
                    int num = x * 10;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        log.error("compute error", e);
                    }
                    return num;
                }
                , EXECUTOR_SERVICE
            ))
            .map(CompletableFuture::join)
            .forEachOrdered(result::add);
        return result;
    }

    /**
     * 使用CompletableFuture.runAsync 让自定义线程池参与工作
     *
     * @param source
     * @return
     */
    private static List<Integer> test3(List<Integer> source) {
        List<Integer> result = new Vector<>();

        List<CompletableFuture<Void>> futures = source.parallelStream()
            .map(s ->
                CompletableFuture.runAsync(() -> {
                    log.info("compute: {}", s);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        log.error("compute error", e);
                    }
                    result.add(s * 10);
                    log.info("{} done", s);
                }, EXECUTOR_SERVICE))
            .collect(toList());
        //futures.forEach(CompletableFuture::join);

        return result;
    }
}
