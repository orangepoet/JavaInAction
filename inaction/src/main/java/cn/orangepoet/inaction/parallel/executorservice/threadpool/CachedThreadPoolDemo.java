package cn.orangepoet.inaction.parallel.executorservice.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chengzhi
 * @date 2020/09/23
 */
@Slf4j
public class CachedThreadPoolDemo {
    private static ExecutorService executors = new ThreadPoolExecutor(1, 100, 3L, TimeUnit.SECONDS,
        new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            final int j = i;
            executors.submit(() -> {
                log.info("seq: {}, thread name: {}", j, Thread.currentThread().getName());
            });
        }
    }
}
