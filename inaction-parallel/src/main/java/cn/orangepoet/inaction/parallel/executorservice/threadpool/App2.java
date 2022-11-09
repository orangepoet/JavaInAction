package cn.orangepoet.inaction.parallel.executorservice.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App2 {
    private static AtomicInteger seq = new AtomicInteger(0);
    //    private static final ExecutorService executorService = Executors.newWorkStealingPool(4);
    private static final ThreadPoolExecutor executorService = new ThreadPoolExecutor(4, 16, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1024), r -> {
        Thread thread = new Thread(r);
        thread.setName("thread" + seq.incrementAndGet());
        return thread;
    });

    public static void main(String[] args) {
        List<Supplier<Integer>> cals = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            final int j = i;
            cals.add(() -> func1(j));
        }

        List<Integer> result = cals.parallelStream()
                .map(supplier -> CompletableFuture.supplyAsync(supplier, executorService))
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        log.info("result: {}", result);
    }

    public static int func1(int i) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("i: {}, finish", i);
        return i;
    }
}
