package cn.orangepoet.inaction.parallel.executorservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用自定义的ForkJoinPool执行并行流, 注意如果使用的不是ForkJoinPool, ParallelStream不会用到
 *
 * @author chengzhi
 * @date 2020/01/02
 */
@Slf4j
public class CustomForkJoinPool {
    private static final ExecutorService EXECUTOR_SERVICE = new ForkJoinPool(2);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> lst = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            lst.add(i);
        }

        List<Integer> result = EXECUTOR_SERVICE.submit(() -> {
            List<Integer> result0 = new ArrayList<>();
            log.info("execute start...");

            lst.parallelStream()
                .map(i -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int num = i * 10;
                    log.info("num: " + num);
                    return num;
                })
                .forEachOrdered(result0::add);
            return result0;
        }).get();

        log.info("result1: " + result);
    }
}
