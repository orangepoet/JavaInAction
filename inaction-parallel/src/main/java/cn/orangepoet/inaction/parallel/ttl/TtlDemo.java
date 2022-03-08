package cn.orangepoet.inaction.parallel.ttl;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

/**
 * @author chengzhi
 * @date 2022/03/03
 */
@Slf4j
public class TtlDemo {
    private static ForkJoinPool forkJoinPool = new ForkJoinPool(16);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("commonPool.size: {}", ForkJoinPool.commonPool().getPoolSize());

        log.info("pool size: {}", forkJoinPool.getPoolSize());

        forkJoinPool.submit(() -> {
            IntStream.range(1, 100).parallel().forEach(i -> {
                try {
                    log.info("task-{}, thread-name: {}", i, Thread.currentThread().getName());
                    Thread.sleep(50);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("task-{} complete", i);
            });
        }).get();
        log.info("pool size: {}", forkJoinPool.getPoolSize());

        Thread.sleep(60000);
        log.info("=======================================");

        forkJoinPool.submit(() -> {
            IntStream.range(1, 5).parallel().forEach(i -> {
                try {
                    log.info("task2-{}, thread-name: {}", i, Thread.currentThread().getName());
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("task2-{} complete", i);
            });
        }).get();
        log.info("pool size: {}", forkJoinPool.getPoolSize());

        log.info("end");
    }

    private static void testTtl() {
        TransmittableThreadLocal<String> ttl = new TransmittableThreadLocal<String>() {
            @Override
            protected void afterExecute() {
                remove();
            }
        };
        ttl.set("hello, world");

        ThreadLocal<String> tl = new ThreadLocal<>();
        tl.set("minemine");

        Thread t = new Thread(() -> {
            log.info("tl1: {}", ttl.get());
            log.info("tl2: {}", tl.get());
        });
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("end, ttl: {}", ttl.get());
    }
}
