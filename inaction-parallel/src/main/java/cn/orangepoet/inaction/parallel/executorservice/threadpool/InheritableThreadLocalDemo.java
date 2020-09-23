package cn.orangepoet.inaction.parallel.executorservice.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chengzhi
 * @date 2020/09/23
 */
@Slf4j
public class InheritableThreadLocalDemo {
    private ThreadLocal<String> threadLocal = new InheritableThreadLocal();

    private static ExecutorService executors = new ThreadPoolExecutor(1, 10, 60L, TimeUnit.SECONDS,
        new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) {
        new InheritableThreadLocalDemo().run();
    }

    public void run() {
        log.info("run...");
        threadLocal.set("parent");

        Thread thread = new Thread(() -> {
            String word = threadLocal.get();
            log.info("child thread, get threadLocal value: {}", word);
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 100; i++) {
            executors.submit(() -> {
                String word = threadLocal.get();
                log.info("threadPool, get threadLocal value: {}", word);
            });
        }
        threadLocal.set("parent2");

        for (int i = 0; i < 100; i++) {
            executors.submit(() -> {
                String word = threadLocal.get();
                log.info("threadPool again, get threadLocal value: {}", word);
            });
        }

        IntStream.range(0, 10).parallel().forEach(num -> {
            String word = threadLocal.get();
            log.info("parallel stream, get threadLocal value: {}", word);
        });

        threadLocal.set("parent3");

        IntStream.range(0, 10).parallel().forEach(num -> {
            String word = threadLocal.get();
            log.info("parallel stream2, get threadLocal value: {}", word);
        });
    }
}
